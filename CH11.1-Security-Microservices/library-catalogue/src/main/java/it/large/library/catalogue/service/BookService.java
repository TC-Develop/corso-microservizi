package it.large.library.catalogue.service;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.entity.BookEntity;
import it.large.library.catalogue.exception.NotFoundException;
import it.large.library.catalogue.model.AuthorModel;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static it.large.library.catalogue.utils.ConverterConfig.*;

@Service
public class BookService {

    // Stiamo iniettando una dipendenza del tipo BookRepository.
    // Questa dipendenza è un'interfaccia che estende JpaRepository e fornisce metodi per l'interazione con l'entità BookEntity nel database.
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

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
        List<BookEntity> bookEntityList = bookRepository.findAll();
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
     * Metodo Post per aggiungere un libro
     */
    // @Transactional Indica che il metodo è transazionale:
    // il che significa che tutte le operazioni all'interno di questo metodo verranno eseguite all'interno di una transazione.
    // Se il metodo ha successo, la transazione verrà confermata (commit)
    // in caso di eccezione, verrà eseguito il rollback delle operazioni della transazione.
    @Transactional
    public BookModel add(BookModel bookModel) {

        AuthorModel authorModel;
        // Semplice controllo se l'autore è già esistente così da associarlo direttamente.
        if (bookModel.getAuthor().getAuthorId() != null) {
            // Si invoca il service di authorService iniettato precedentemente, in alto per recuperare l'autore.
            authorModel = authorService.get(bookModel.getAuthor().getAuthorId());
        } else {
            authorModel = bookModel.getAuthor();
        }

        // Per permettere le operazioni su due entità nello stesso salvataggio bisogna impostare il CascadeType.ALL su entrambe le associazioni tra le due entità.
        bookModel.setAuthor(authorModel);
        BookEntity bookEntity = convertBookModelToBookEntity(bookModel);
        // Salviamo il libro con all'interno l'autore e la categoria/e indicata/e in input.
        bookEntity = bookRepository.save(bookEntity);

        return convertBookEntityToBookModel(bookEntity);
    }

    /*
     * Metodo Post per modificare un libro
     */
    @Transactional
    public BookModel edit(UUID bookId, BookModel bookModel) {

        // Stiamo utilizzando il metodo findById (di JPA) di bookRepository per cercare un libro nel database in base al suo ID.
        // Il risultato è avvolto in un Optional, che è un contenitore che può contenere o meno un valore.
        Optional<BookEntity> bookEntityOpt = bookRepository.findById(bookId);
        if (bookEntityOpt.isEmpty()) {
            throw new NotFoundException("Libro non presente in catalogo");
        }

        AuthorModel authorModel;
        // Semplice controllo se l'autore è già esistente così da associarlo direttamente.
        if (bookModel.getAuthor().getAuthorId() != null) {
            // Si invoca il service di authorService iniettato precedentemente, in alto per recuperare l'autore.
            authorModel = authorService.get(bookModel.getAuthor().getAuthorId());
        } else {
            authorModel = bookModel.getAuthor();
        }

        // Impostiamo l'autore al libro
        bookModel.setAuthor(authorModel);
        // Impostiamo l'id del libro già presente in DB per poter modificare il libro desiderato ed evitare di generarne uno nuovo con un nuovo id.
        bookModel.setBookId(bookEntityOpt.get().getBookId());
        BookEntity bookEntity = convertBookModelToBookEntity(bookModel);
        bookEntity = bookRepository.save(bookEntity);

        return convertBookEntityToBookModel(bookEntity);
    }

    /*
     * Metodo Post per eliminare un libro
     */
    public void remove(UUID bookId) {
        Optional<BookEntity> bookEntityOpt = bookRepository.findById(bookId);
        if (bookEntityOpt.isEmpty()) {
            throw new NotFoundException("Libro non presente in catalogo");
        }

        bookRepository.delete(bookEntityOpt.get());
    }

}