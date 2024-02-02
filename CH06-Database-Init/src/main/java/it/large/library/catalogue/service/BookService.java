package it.large.library.catalogue.service;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.model.BookModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    /*
     * Metodo Get per recupero di un libro tramite il suo id
     */
    public BookModel getBook(UUID bookId) {

        return null;
    }

    /*
     * Metodo get per filtrare e recuperare i libri in base a dei parametri in input
     */
    public List<BookModel> getAllFilter(String titleBook, BigDecimal price) {
        return null;
    }

    /*
     * Metodo Get per filtrare e recuperare i libri in base ai parametri in input all'interno di un oggetto di filtro
     */
    public List<BookModel> getAllFilteredObject(BookFilter bookFilter) {
        return null;
    }

    /*
     * Metodo Post per aggiungere un libro (DI ESEMPIO)
     */
    public BookModel add(BookModel bookModel) {
        return null;
    }

    /*
     * Metodo Post per modificare un libro
     */
    public BookModel edit(UUID bookId, BookModel bookModel) {
        return null;

    }

    /*
     * Metodo Post per eliminare un libro
     */
    public Boolean remove(UUID bookId) {

        return null;
    }

}