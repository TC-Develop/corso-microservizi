package it.large.library.sales.service;

import it.large.library.sales.client.CatalogueClient;
import it.large.library.sales.client.response.ExternalBookResponse;
import it.large.library.sales.document.SaleDocument;
import it.large.library.sales.exception.NotFoundException;
import it.large.library.sales.model.BookModel;
import it.large.library.sales.model.ClientTotalAmount;
import it.large.library.sales.model.SaleModel;
import it.large.library.sales.repository.SaleRepository;
import it.large.library.sales.utils.ConverterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    // Si inietta l'interfaccia responsabile di gestire le call al microservizio di catalogo dei libri.
    @Autowired
    private CatalogueClient catalogueClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    public SaleModel add(SaleModel saleModel) {
        // Metodo privato per recuperare le informazioni dei libri sul microservizio di catalogo dei libri.
        List<BookModel> internalBookModels = retrieveBooksFromCatalogue(saleModel);

        // Metodo per calcolare il totale dell'importo della vendita dei libri.
        Double totalAmount = getTotalAmount(internalBookModels);

        // Impostiamo i dati necessari all'oggetto model di vendita.
        saleModel.setBooks(internalBookModels);
        saleModel.setPurchaseDate(Timestamp.from(Instant.now()));
        saleModel.setAmount(totalAmount);

        // Conversione del model a document, per salvare la vendita in db.
        SaleDocument saleDocument = ConverterConfig.convertModelToDocument(saleModel);
        // Impostiamo un UUID randomico perché non getsito da mongo.
        saleDocument.setSaleId(UUID.randomUUID());
        // Salvataggio della vendita
        saleDocument = saleRepository.save(saleDocument);
        saleModel = ConverterConfig.convertDocumentToModel(saleDocument);

        return saleModel;
    }

    private List<BookModel> retrieveBooksFromCatalogue(SaleModel saleModel) {

        List<BookModel> internalBookModels = new ArrayList<>();
        for (BookModel bookModel: saleModel.getBooks()) {
            BookModel internalBookModel = new BookModel();
            ExternalBookResponse externalBookResponse;
            try {
                // Per ogni id inviato in input andiamo a controllare l'esistenza del libro e del recupero delle informaizoni del libro.
                externalBookResponse = catalogueClient.getBookById(bookModel.getBookId()).getBody();
                if (externalBookResponse != null) {
                    // Associamo le info dei libri recuperate, all'oggetto libro del microservizio di vendita.
                    internalBookModel.setBookId(externalBookResponse.getBookId());
                    internalBookModel.setPrice(externalBookResponse.getPrice());
                    internalBookModel.setTitle(externalBookResponse.getTitle());
                    internalBookModels.add(internalBookModel);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw new NotFoundException("Libro non trovato: " + e.getMessage());
            }
        }
        return internalBookModels;
    }

    private static Double getTotalAmount(List<BookModel> internalBookModels) {
        Double totalAmount = 0D;
        for (BookModel book: internalBookModels) {
            totalAmount = totalAmount + book.getPrice();
        }
        return totalAmount;
    }


    public List<SaleModel> get(UUID clientId) {
        // Recupera le vendite legate ad uno specifico cliente
        List<SaleDocument> saleDocumentList = saleRepository.getSalesByClientId(clientId);

        return ConverterConfig.convertDocumentListToModelList(saleDocumentList);
    }


    public Double getTotalAmountPerClient(UUID clientId) {
        // [
        //    {
        //    $match: {
        //        clientId: UUID("779a5ffc729c4c8a9c27b8bc0f0d5dc2")
        //        }
        //    },
        //    {
        //    $group: {
        //        _id: '$clientId',
        //        totalAmount: {
        //            $sum: '$amount'
        //            }
        //        }
        //    }
        //]
        // Operazione di corrispondenza per filtrare le vendite per il clientId specificato
        AggregationOperation matchOperation = Aggregation.match(Criteria.where("clientId").is(clientId));

        // Operazione di raggruppamento per raggruppare le vendite in base al clientId e calcolare la somma degli importi
        GroupOperation groupOperation = Aggregation.group("clientId").sum("amount").as("totalAmount");

        // Creazione di un'istanza di Aggregation che incorpora le operazioni di match e group
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);

        // Esecuzione dell'aggregazione sulla collezione "sales" e mappatura dei risultati a una classe modello
        ClientTotalAmount results = mongoTemplate.aggregate(aggregation, "sales", ClientTotalAmount.class).getUniqueMappedResult();

        // Restituzione del risultato finale, ovvero la somma totale degli importi per il clientId specificato
        return results.getTotalAmount();
    }

}