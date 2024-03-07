package it.large.library.sales;

import it.large.library.sales.client.CatalogueClient;
import it.large.library.sales.client.response.ExternalBookResponse;
import it.large.library.sales.controller.payload.request.SaleRequest;
import it.large.library.sales.controller.payload.response.BookResponse;
import it.large.library.sales.controller.payload.response.SaleResponse;
import it.large.library.sales.document.BookDocument;
import it.large.library.sales.document.SaleDocument;
import it.large.library.sales.exception.NotFoundException;
import it.large.library.sales.repository.SaleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaleControllerTests {

    @LocalServerPort
    private int port;

    @MockBean
    private CatalogueClient catalogueClient;

    @MockBean
    private SaleRepository repository;


    //è una classe fornita dal framework Spring, in particolare dal modulo Spring Boot, che semplifica il testing
    // delle applicazioni che fanno chiamate HTTP.
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final UUID idBook = UUID.fromString("b89b907d-423e-4831-9132-b6be77cb24a7");
    private static final String clientId = "fdf61485-31cc-447b-b749-13ad2dbe083b";

    private ResponseEntity<SaleResponse> requestHttp(Serializable bookRequest, HttpMethod method, String path) {
        HttpHeaders headers = new HttpHeaders();
            headers.set("client_id",clientId );

        HttpEntity<Serializable> httpEntity = new HttpEntity<>(bookRequest,headers);

        return testRestTemplate.exchange(
                path,
                method,
                httpEntity,
                //new ParameterizedTypeReference<>() {}: Viene utilizzato per ottenere una rappresentazione tipizzata della risposta.
                // In questo caso, <>() indica che si sta creando un'istanza di ParameterizedTypeReference
                // con il tipo generico inferito. La risposta verrà quindi interpretata in base al tipo.
                new ParameterizedTypeReference<>() {}
        );
    }
    private ResponseEntity<List<SaleResponse>> requestHttpForGet(Serializable bookRequest, HttpMethod method, String path) {

        HttpEntity<Serializable> httpEntity = new HttpEntity<>(bookRequest);

        return testRestTemplate.exchange(
                path,
                method,
                httpEntity,
                //new ParameterizedTypeReference<>() {}: Viene utilizzato per ottenere una rappresentazione tipizzata della risposta.
                // In questo caso, <>() indica che si sta creando un'istanza di ParameterizedTypeReference
                // con il tipo generico inferito. La risposta verrà quindi interpretata in base al tipo.
                new ParameterizedTypeReference<>() {}
        );
    }

    /**
     *
     * @return Oggetto SaleRequest valorizzato con attributi che ci aspettiamo
     */
    private SaleRequest buildRequest(List<UUID> bookIds){
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setBooksIds(bookIds);

        return saleRequest;
    }


    private List<BookDocument> buildBooksList(List<UUID> bookIds){
        List<BookDocument> bookDocuments = new ArrayList<>();
        int i = 1;

        for (UUID uuid : bookIds){
            BookDocument bookDocument = new BookDocument();
            bookDocument.setBookId(uuid);
            bookDocument.setTitle("TITOLO" + i );
            bookDocument.setPrice((double) (i * 5));
            i++;
            bookDocuments.add(bookDocument);
        }

        return bookDocuments;
    }



    private ResponseEntity<ExternalBookResponse> buildBookResponse(){

        ExternalBookResponse bookResponse = new ExternalBookResponse();
        bookResponse.setBookId(idBook);
        bookResponse.setPrice(100.0);
        bookResponse.setTitle("TITOLO");

        return ResponseEntity.ok(bookResponse);
    }

    private SaleDocument buildSaleDocument(){
        SaleDocument salesDocument = new SaleDocument();
        salesDocument.setSaleId(UUID.randomUUID());
        salesDocument.setClientId(UUID.randomUUID());
        salesDocument.setPurchaseDate(Instant.now());
        salesDocument.setAmount(100.0);
        salesDocument.setBooks(buildBooksList(List.of(idBook)));

        return salesDocument;
    }


    public static void checkFields(SaleDocument entity, SaleResponse response) {
        // Verifica che gli attributi siano uguali utilizzando assertEquals
        assertEquals(entity.getSaleId(), response.getSaleId());
        assertEquals(entity.getAmount(), response.getAmount());
        assertEquals(entity.getClientId(), response.getClientId());
        assertEquals(entity.getPurchaseDate(), response.getPurchaseDate());
    }

    public static void checkFields(SaleRequest request, SaleResponse response) {
        // Verifica che gli attributi siano uguali utilizzando assertEquals
        assertEquals(request.getBooksIds(),response.getBooks().stream().map(BookResponse::getBookId).toList());
    }




    /**
     * Metodo che controlla il corretto funzionamento delle operazioni d'inserimento a db
     */
    @Test
    @Order(100)
    void postOk() {


        UUID idToPersistSales = UUID.fromString("a627d770-cc0f-4521-89ae-a9749bd91381");
        SaleDocument saleDocument = buildSaleDocument();

        when(repository.findAll()).thenReturn(List.of(saleDocument));

        when(repository.findById(idToPersistSales)).thenReturn(Optional.of(saleDocument));

        when(catalogueClient.getBookById(idBook)).thenReturn(buildBookResponse());

        when(repository.save(any())).thenReturn(saleDocument);

        //Recupero del numero di elementi presenti a db prima della richiesta
        Integer numberElementsBefore = repository.findAll().size();

        //Verifico che i campi ritornati dal db siano uguali a quelli che mi aspetto di ricevere
        SaleRequest saleRequest = buildRequest(List.of(idBook));

        //Richiesta d'inserimento
        ResponseEntity<SaleResponse> response =
                requestHttp(saleRequest, HttpMethod.POST, "http://localhost:" + port + "/library/api/v1/sales");

        //Recupero del numero di elementi presenti a db dopo la richiesta
        Integer numberElementsAfter = repository.findAll().size();

        //Verifica che la response non sia null
        Assertions.assertNotNull(response.getBody());

        //Recupero dei dati tornati della response
        SaleResponse saleResponse = response.getBody();

        //Controllo sul cambiamento del numero di elementi
//        Assertions.assertEquals(numberElementsAfter,numberElementsBefore + 1);

        //Controllo lo status
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());

        //Verifica che i campi della request siano stati effettivamente rispettati
        checkFields(saleRequest,saleResponse);

        Optional<SaleDocument> entityOpt = repository.findById(idToPersistSales);

        //Verifica che l'elemento sia stato recuperato
        Assertions.assertTrue(entityOpt.isPresent());

        //Verifica che i campi siano uguali a quelli salvati a db
        checkFields(entityOpt.get(),saleResponse);
    }

    @Test
    @Order(200)
    void getByIdOk() {

        UUID idClient = UUID.fromString("3a3a6682-2854-4b12-9f9b-1023c7f497d4");
        SaleDocument saleDocument = buildSaleDocument();

        when(repository.getSalesByClientId(idClient)).thenReturn(List.of(saleDocument));


        List<SaleDocument> entityOpt = repository.getSalesByClientId(idClient);

        //Verifica che elementi siano recuperati dal db
        Assertions.assertFalse(entityOpt.isEmpty());

        //Richiesta d'inserimento
        ResponseEntity<List<SaleResponse>> response =
                requestHttpForGet(null, HttpMethod.GET, "http://localhost:" + port + "/library/api/v1/sales/"+idClient);

        //Controllo lo stato
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //Verifica che la response non sia null
        Assertions.assertNotNull(response.getBody());

    }


    @Test
    @Order(300)
    void getByIdNotOk() {

        UUID idClientNotOk = UUID.fromString("3a3a6682-2854-4b12-9f9b-1023c7f497d4");

        UUID idClientOk = UUID.fromString("87740b58-5eb0-4f6c-b3d9-b664e8fd6c5f");
        SaleDocument saleDocument = buildSaleDocument();

        when(repository.getSalesByClientId(idClientOk)).thenReturn(List.of(saleDocument));

        when(repository.getSalesByClientId(idClientNotOk)).thenThrow(new NotFoundException("ERRORE"));

        //Richiesta d'inserimento Da cui mi aspetto una lista vuota
        ResponseEntity<List<SaleResponse>> response =
                requestHttpForGet(null, HttpMethod.GET, "http://localhost:" + port + "/library/api/v1/sales/"+idClientNotOk);

        //Richiesta d'inserimento che mi aspetto restituisca qualcosa
        ResponseEntity<List<SaleResponse>> responseOk =
                requestHttpForGet(null, HttpMethod.GET, "http://localhost:" + port + "/library/api/v1/sales/"+idClientOk);

        //Verifica che la response Torni una lista vuota
        Assertions.assertTrue(response.getBody().isEmpty());

        //Verifica che la response sia Valorizzata
        Assertions.assertFalse(responseOk.getBody().isEmpty());

    }

    @Test
    @Order(400)
    void getByIdWithVerify() {
        UUID idClientOk = UUID.fromString("87740b58-5eb0-4f6c-b3d9-b664e8fd6c5f");
        SaleDocument saleDocument = buildSaleDocument();

        when(repository.getSalesByClientId(idClientOk)).thenReturn(List.of(saleDocument));

        //Richiesta d'inserimento che mi aspetto restituisca qualcosa
        ResponseEntity<List<SaleResponse>> responseOk =
                requestHttpForGet(null, HttpMethod.GET, "http://localhost:" + port + "/library/api/v1/sales/"+idClientOk);

        // Verifica che il metodo getSalesByClientId sia stato chiamato esattamente una volta con l'argomento idClientOk
        //verify è un metodo di Mockito che viene utilizzato per verificare il comportamento di un mock. In particolare,
        // viene utilizzato per verificare se un determinato metodo del mock è stato chiamato con specifici argomenti o meno.
        verify(repository, times(1)).getSalesByClientId(idClientOk);

        //Verifica che la response sia Valorizzata
        Assertions.assertFalse(responseOk.getBody().isEmpty());

        Assertions.assertEquals(HttpStatus.OK,responseOk.getStatusCode());
    }

}
