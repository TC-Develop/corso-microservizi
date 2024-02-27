package it.large.library.catalogue;

import it.large.library.catalogue.controller.BookController;
import it.large.library.catalogue.controller.payload.request.AuthorRequest;
import it.large.library.catalogue.controller.payload.request.BookRequest;
import it.large.library.catalogue.controller.payload.response.BookResponse;
import it.large.library.catalogue.entity.BookEntity;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// questa annotazione permette di eseguire test di integrazione caricando l'intero contesto dell'applicazione Spring
// in un ambiente web, con una porta casuale assegnata per il server incorporato.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// E' utilizzata per specificare l'ordine di esecuzione dei metodi di test in una classe di test
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTests {


    @Autowired
    private BookRepository bookRepository;


    //è una classe fornita dal framework Spring, in particolare dal modulo Spring Boot, che semplifica il testing
    // delle applicazioni che fanno chiamate HTTP.
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final UUID idCategoryTest = UUID.fromString("2ca129e6-9b17-457f-a716-5d6ad5cf44e0");

    @LocalServerPort
    private int port;

    private ResponseEntity<BookResponse> requestHttp(Serializable bookRequest, HttpMethod method, String path) {
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
     * @return Oggetto bookModel valorizzato con attributi che ci aspettiamo
     */
    private BookRequest buildRequest(UUID authorId, String name, String birthday, String surname, String birthCity, UUID bookId, String title, BigDecimal price ){
        BookRequest book = new BookRequest();
        AuthorRequest author = new AuthorRequest();
        author.setAuthorId(authorId);
        author.setName(name);
        author.setSurname(surname);
        author.setBirthday(Date.from(LocalDateTime.parse(birthday).atZone(ZoneId.systemDefault()).toInstant()));
        author.setDeathDay(null);
        author.setBirthCity(birthCity);

        book.setAuthor(author);
        book.setBookId(bookId);
        book.setTitle(title);
        book.setPrice(price);
        book.setCategoriesIds(Set.of(idCategoryTest));

        return book;
    }

    public static void checkFields(BookRequest request, BookResponse response) {
        // Verifica che gli attributi siano uguali utilizzando assertEquals
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getPrice(), response.getPrice());

    }
    public static void checkFields(BookEntity entity, BookResponse response) {
        // Verifica che gli attributi siano uguali utilizzando assertEquals
        assertEquals(entity.getBookId(), response.getBookId());
        assertEquals(entity.getTitle(), response.getTitle());
        assertEquals(entity.getPrice(), response.getPrice());

    }

    private ResponseEntity<Boolean> requestHttpDelete(Serializable bookRequest,HttpMethod method, String path) {
        HttpEntity<Serializable> httpEntity = new HttpEntity<>(bookRequest, null);

        return testRestTemplate.exchange(
                path,
                method,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );
    }

    /**
     * Metodo che controlla il corretto funzionamento delle operazioni d'inserimento a db
     */
    @Test
    @Order(100)
    void postOk() {

        //Recupero del numero di elementi presenti a db prima della richiesta
        Integer numberElementsBefore = bookRepository.findAll().size();

        //Verifico che i campi ritornati dal db siano uguali a quelli che mi aspetto di ricevere
        BookRequest bookRequest = buildRequest(null
                ,"TEST_GETBOOKBYREALID","1990-01-01T00:00:00","TEST_GETBOOKBYREALID"
                ,"TEST_GETBOOKBYREALID",null
                ,"TEST_GETBOOKBYREALID",BigDecimal.valueOf(20));


        //Richiesta d'inserimento
        ResponseEntity<BookResponse> response =
                requestHttp(bookRequest, HttpMethod.POST, "http://localhost:" + port + "/catalogue/api/v1/book");


        //Recupero del numero di elementi presenti a db dopo la richiesta
        Integer numberElementsAfter = bookRepository.findAll().size();

        //Verifica che la response non sia null
        Assertions.assertNotNull(response.getBody());

        //Recupero dei dati tornati della response
        BookResponse bookResponse = response.getBody();

        //Controllo sul cambiamento del numero di elementi
        Assertions.assertEquals(numberElementsAfter,numberElementsBefore + 1);

        //Controllo lo status
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());

        //Verifica che i campi della request siano stati effettivamente rispettati
        checkFields(bookRequest,bookResponse);

        Optional<BookEntity> entityOpt = bookRepository.findById(bookResponse.getBookId());

        //Verifica che l'elemento sia stato recuperato
        Assertions.assertTrue(entityOpt.isPresent());

        //Verifica che i campi siano uguali a quelli salvati a db
        checkFields(entityOpt.get(),bookResponse);
    }

    @Test
    @Order(200)
    void getByIdOk() {

        UUID idBook = UUID.fromString("3a3a6682-2854-4b12-9f9b-1023c7f497d4");

        Optional<BookEntity> entityOpt = bookRepository.findById(idBook);

        //Verifica che l'elemento sia recuperato dal db
        Assertions.assertTrue(entityOpt.isPresent());

        //Richiesta d'inserimento
        ResponseEntity<BookResponse> response =
                requestHttp(null, HttpMethod.GET, "http://localhost:" + port + "/catalogue/api/v1/book/"+idBook);

        //Controllo lo stato
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        //Verifica che la response non sia null
        Assertions.assertNotNull(response.getBody());

    }

    @Test
    @Order(300)
    void getByIdNotFound() {

        UUID idBook = UUID.randomUUID();

        Optional<BookEntity> entityOpt = bookRepository.findById(idBook);

        //Verifica che l'elemento non sia recuperato dal db
        Assertions.assertTrue(entityOpt.isEmpty());

        //Richiesta d'inserimento
        ResponseEntity<BookResponse> response =
                requestHttp(null, HttpMethod.GET, "http://localhost:" + port + "/catalogue/api/v1/book/"+idBook);

        //Controllo lo stato
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }



}
