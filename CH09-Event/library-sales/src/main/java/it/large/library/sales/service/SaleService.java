package it.large.library.sales.service;

import it.large.library.sales.client.CatalogueClient;
import it.large.library.sales.client.response.ExternalBookResponse;
import it.large.library.sales.document.SaleDocument;
import it.large.library.sales.event.SaleBookEvent;
import it.large.library.sales.exception.NotFoundException;
import it.large.library.sales.model.BookModel;
import it.large.library.sales.model.ClientTotalAmount;
import it.large.library.sales.model.SaleModel;
import it.large.library.sales.repository.SaleRepository;
import it.large.library.sales.utils.ConverterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /*
     * ApplicationEventPublisher è un'interfaccia in Spring Framework che consente agli oggetti di pubblicare eventi all'interno di un'applicazione.
     * Gli eventi possono essere ascoltati da altri componenti che sono interessati a reagire a tali eventi.
     * @Autowired ApplicationEventPublisher applicationEventPublisher:
     *          Spring si occupa di fornire un'istanza di ApplicationEventPublisher al componente quando viene creato dal contesto dell'applicazione.
     *          Successivamente, si può utilizzare questa istanza per pubblicare eventi all'interno della tua applicazione.
     */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /*
     * Aggiunge una vendita di libri
     */
    public SaleModel add(SaleModel saleModel) {
        // Metodo privato per recuperare le informazioni dei libri sul microservizio di catalogo dei libri.
        List<BookModel> internalBookModels = retrieveBooksFromCatalogue(saleModel);

        // Metodo per calcolare il totale dell'importo della vendita dei libri.
        BigDecimal totalAmount = getTotalAmount(internalBookModels);

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

        // Quando viene chiamato applicationEventPublisher.publishEvent(saleBookEvent), si sta notificando l'applicazione di un evento specifico (SaleBookEvent).
        // Tutti i bean interessati a questo tipo di evento possono reagire eseguendo azioni specifiche.
        SaleBookEvent saleBookEvent = new SaleBookEvent(saleModel);
        applicationEventPublisher.publishEvent(saleBookEvent);

        return saleModel;
    }

    /*
     * Metodo privato interno per recuperare le informazioni dei libri dal microservizio di catalogo
     */
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
                    internalBookModel.setQuantity(bookModel.getQuantity());
                    internalBookModels.add(internalBookModel);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw new NotFoundException("Libro non trovato: " + e.getMessage());
            }
        }
        return internalBookModels;
    }

    /*
     * Metodo privato interno per determinare l'ammontare il costo della vendita
     */
    private static BigDecimal getTotalAmount(List<BookModel> internalBookModels) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BookModel book: internalBookModels) {
            totalAmount = totalAmount.add(book.getPrice());
        }
        return totalAmount;
    }

    /*
     * Recupera le vendite di uno specifico cliente
     */
    public List<SaleModel> get(UUID clientId) {
        // Recupera le vendite legate ad uno specifico cliente con query nativa mongo (vedere saleRepository)
        List<SaleDocument> saleDocumentList = saleRepository.getSalesByClientId(clientId);

        return ConverterConfig.convertDocumentListToModelList(saleDocumentList);
    }

    /*
     * Recupera l'ammonatare del costo delle vendite di uno specifico cliente (con aggregation mongo)
     */
    public BigDecimal getTotalAmountPerClient(UUID clientId) {
        // Questa è una semplice aggregation con operatore $sum, per calcolare la somma del costo di tutte le vendite di un cliente.
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