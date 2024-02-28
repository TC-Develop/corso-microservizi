package it.large.library.sales.client;

import it.large.library.sales.ConfigurationFeign;
import it.large.library.sales.client.response.ExternalBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;
// Un'annotazione di Spring Cloud Feign che identifica l'interfaccia come un client Feign.
// name = "libraryCatalogueClient": Specifica il nome del client Feign, che può essere utilizzato come identificatore univoco
// per risolvere il client all'interno dell'applicazione.
// url = "${feign.url.catalogue.host}:${feign.url.catalogue.port}": Specifica l'URL di base a cui saranno indirizzate tutte le richieste del client Feign.
// La notazione ${feign.url.catalogue.host} e ${feign.url.catalogue.port} indica che questi valori verranno presi dalla configurazione di Spring Boot. Questo approccio consente di configurare dinamicamente l'URL del client senza modificare direttamente il codice.
@FeignClient(name = "libraryCatalogueClient", configuration = ConfigurationFeign.class, url = "${feign.url.catalogue.host}:${feign.url.catalogue.port}")
public interface CatalogueClient {

    @RequestMapping(method = RequestMethod.GET, value = "catalogue/api/v1/book/{book_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ExternalBookResponse> getBookById(
            @PathVariable("book_id") UUID BookId
    );

}