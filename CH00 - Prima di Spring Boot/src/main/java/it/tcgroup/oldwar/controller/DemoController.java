package it.tcgroup.oldwar.controller;

import it.tcgroup.oldwar.controller.payload.request.BookRequest;
import it.tcgroup.oldwar.controller.payload.response.BookResponse;
import it.tcgroup.oldwar.service.BookService;
import it.tcgroup.oldwar.service.model.Book;
import it.tcgroup.oldwar.util.ClassCoverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class DemoController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/book")
    public ResponseEntity<List<BookResponse>> home(){
        List<Book> bookList = bookService.getAll();

        List<BookResponse> response = new ArrayList<>();
        for(Book book : bookList){
            response.add(ClassCoverter.BookToBookResponse(book));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/book/{id}")
    public ResponseEntity<BookResponse> getById(
            @PathVariable(name = "id") UUID id){
        Book book = bookService.getById(id);
        BookResponse bookResponse = ClassCoverter.BookToBookResponse(book);

        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping(path = "/book")
    public ResponseEntity<BookResponse> addBook(
            @RequestBody BookRequest bookRequest){
        Book book = ClassCoverter.BookRequestToBook(bookRequest);
        book = bookService.save(book);
        BookResponse response = ClassCoverter.BookToBookResponse(book);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/book/{id}")
    public ResponseEntity<BookResponse> editBook(
            @PathVariable( name = "id") String id,
            @RequestBody BookRequest bookRequest){
        Book book = ClassCoverter.BookRequestToBook(bookRequest);
        book = bookService.edit(book, UUID.fromString(id));
        BookResponse response = ClassCoverter.BookToBookResponse(book);

        return ResponseEntity.ok(response);
    }

}
