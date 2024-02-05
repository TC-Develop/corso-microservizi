package it.tcgroup.oldwar.controller;

import it.tcgroup.oldwar.controller.payload.request.BookRequest;
import it.tcgroup.oldwar.controller.payload.response.BookResponse;
import it.tcgroup.oldwar.util.ClassCoverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;

@RestController
public class DemoController {

    @GetMapping(path = "/book")
    public ResponseEntity<BookResponse> home(){
        BookResponse response = new BookResponse();
        response.setAutor("T&C");
        response.setTitle("Microservizi Java Spring");
        response.setCode("80019301248123");
        response.setPrice(new BigDecimal(12.31).setScale(2, RoundingMode.HALF_EVEN));
        response.setReleaseDate(new Date());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/book")
    public ResponseEntity<BookResponse> addBook(
            @RequestBody BookRequest bookRequest){
        BookResponse response = ClassCoverter.BookRequestToBookResponse(bookRequest);
        response.setCode(UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

}
