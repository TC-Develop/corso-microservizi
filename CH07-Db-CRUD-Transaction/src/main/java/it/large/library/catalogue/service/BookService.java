package it.large.library.catalogue.service;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.entity.BookEntity;
import it.large.library.catalogue.exception.NotFoundException;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static it.large.library.catalogue.utils.ConverterConfig.convertBookEntitiesToBookModels;
import static it.large.library.catalogue.utils.ConverterConfig.convertBookEntityToBookModel;

@Service
public class BookService {

    // Stiamo iniettando una dipendenza del tipo BookRepository.
    // Questa dipendenza è un'interfaccia che estende JpaRepository e fornisce metodi per l'interazione con l'entità BookEntity nel database.
    @Autowired
    private BookRepository bookRepository;

    /*
     * Metodo Get per recupero di un libro tramite il suo id
     */
    public BookModel getBook(UUID bookId) {

        // Stiamo utilizzando il metodo findById (di JPA) di bookRepository per cercare un libro nel database in base al suo ID.
        // Il risultato è avvolto in un Optional, che è un contenitore che può contenere o meno un valore.
        Optional<BookEntity> bookEntityOpt = bookRepository.findById(bookId);
        if (bookEntityOpt.isEmpty()) {
            throw new NotFoundException("Libro non presente in catalogo");
        }
        // Convertiamo l'oggetto entità in model
        BookModel bookModel = convertBookEntityToBookModel(bookEntityOpt.get());

        return bookModel;
    }

    /*
     * Metodo get per filtrare e recuperare i libri in base a dei parametri in input
     */
    public List<BookModel> getAllFilter(String titleBook, BigDecimal price) {
        // Libri trovati in db grazie al metodo della repository di BookEntity (Vedere metodo in BookRepository)
        List<BookEntity> bookEntityList = bookRepository.getByTitleOrPrice(titleBook, price);
        // Convertiamo le entità libri in model
        List<BookModel> bookModelList = convertBookEntitiesToBookModels(bookEntityList);

        return bookModelList;
    }

    /*
     * Metodo Get per filtrare e recuperare i libri in base ai parametri in input all'interno di un oggetto di filtro
     */
    public List<BookModel> getAllFilteredObject(BookFilter bookFilter) {
        // Libri recuperati in db grazie al filtraggio inviato in input (Vedere BookRepository)
        List<BookEntity> bookEntityList = bookRepository.filter(bookFilter);
        // Convertiamo le entità libri in model
        List<BookModel> bookModelList = convertBookEntitiesToBookModels(bookEntityList);

        return bookModelList;
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