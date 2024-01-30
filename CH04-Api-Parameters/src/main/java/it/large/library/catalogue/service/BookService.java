package it.large.library.catalogue.service;

import it.large.library.catalogue.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    public BookModel add(BookModel bookModel) {
        bookModel.setBookId(UUID.randomUUID());
        System.out.println("E' stato inserito il libro: " + bookModel.getTitle());

        return bookModel;
    }

}
