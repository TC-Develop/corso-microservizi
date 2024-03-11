package it.large.library.sales.event;

import it.large.library.sales.client.CatalogueClient;
import it.large.library.sales.client.request.ExternalBookQuantityRequest;
import it.large.library.sales.model.BookModel;
import it.large.library.sales.model.SaleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryEventServiceManagement {

    @Autowired
    private CatalogueClient catalogueClient;

    // L'annotazione @Async indica a Spring di eseguire il metodo in un thread separato in modo asincrono rispetto al chiamante.
    // Questo è utile per operazioni che possono essere eseguite in modo indipendente e non influenzano il flusso principale dell'applicazione.
    // In questo caso, il metodo reduceBookQuantity sarà eseguito in modo asincrono.
    @Async
    // @TransactionalEventListener: Questa annotazione identifica il metodo come un ascoltatore di eventi transazionali.
    // Il metodo reduceBookQuantity verrà chiamato quando si verifica un evento di tipo SaleBookEvent dopo il completamento della transazione.
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            classes = SaleBookEvent.class,
            condition ="#saleBookEvent.getSource().getAmount() > 0"
//            fallbackExecution = true
    )
    public void reduceBookQuantity(SaleBookEvent saleBookEvent) {
        // Questo metodo è in ascolto nei punti in cui viene avviato l'evento SaleBookEvent (vedere SaleService rigo 75).
        // Apena invocato dall'evento SaleBookEvent, e dopo la buona riuscita della transazione della vendita, avvia il feign client verso Catalogue, per modificare le quanità dei libri venduti.
        SaleModel saleModel = (SaleModel) saleBookEvent.getSource();
        List<ExternalBookQuantityRequest> externalBookQuantityRequests = new ArrayList<>();
        for (BookModel bookModel: saleModel.getBooks()) {
            ExternalBookQuantityRequest bookQuantityRequest = new ExternalBookQuantityRequest();
            bookQuantityRequest.setBookId(bookModel.getBookId());
            bookQuantityRequest.setQuantity(bookModel.getQuantity());
            externalBookQuantityRequests.add(bookQuantityRequest);
        }
        catalogueClient.editQuantity(externalBookQuantityRequests);
    }

}