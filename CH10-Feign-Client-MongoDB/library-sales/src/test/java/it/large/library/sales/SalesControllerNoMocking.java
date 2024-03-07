package it.large.library.sales;

import it.large.library.sales.controller.payload.request.SaleRequest;
import it.large.library.sales.controller.payload.response.BookResponse;
import it.large.library.sales.controller.payload.response.SaleResponse;
import it.large.library.sales.document.SaleDocument;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SalesControllerNoMocking {

    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final UUID idBook = UUID.fromString("b89b907d-423e-4831-9132-b6be77cb24a7");
    private static final String clientId = "fdf61485-31cc-447b-b749-13ad2dbe083b";

    private ResponseEntity<SaleResponse> requestHttp(Serializable bookRequest, HttpMethod method, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("client_id", clientId);

        HttpEntity<Serializable> httpEntity = new HttpEntity<>(bookRequest, headers);

        return testRestTemplate.exchange(
                path,
                method,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private SaleRequest buildRequest(List<UUID> bookIds) {
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setBooksIds(bookIds);
        return saleRequest;
    }

    public static void checkFields(SaleDocument entity, SaleResponse response) {
        assertEquals(entity.getSaleId(), response.getSaleId());
        assertEquals(entity.getAmount(), response.getAmount());
        assertEquals(entity.getClientId(), response.getClientId());
        assertEquals(entity.getPurchaseDate(), response.getPurchaseDate());
    }

    public static void checkFields(SaleRequest request, SaleResponse response) {
        assertEquals(request.getBooksIds(), response.getBooks().stream().map(BookResponse::getBookId).toList());
    }

    @Test
    @Order(100)
    void postOk() {
        SaleRequest saleRequest = buildRequest(List.of(idBook));

        ResponseEntity<SaleResponse> response = requestHttp(saleRequest, HttpMethod.POST, "http://localhost:" + port + "/library/api/v1/sales");


        Assertions.assertNotNull(response.getBody());
        SaleResponse saleResponse = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        checkFields(saleRequest, saleResponse);
    }
}
