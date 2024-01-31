package it.large.library.catalogue.service;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.model.BookModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.large.library.catalogue.utils.StaticDatabase.getAllBooksStatic;

@Service
public class BookService {

    // La lista statica dei libri in storage
    private static final List<BookModel> bookModels = getAllBooksStatic();

    /*
     * Metodo Get per recupero di un libro tramite il suo id
     */
    public BookModel getBook(UUID bookId) {
        // Controlla se esiste un libro con quell'id specifico
        for (BookModel bookModel: bookModels) {
            if (bookModel.getBookId().equals(bookId)) {
                return bookModel;
            }
        }
        return null;
    }

    /*
     * Metodo get per filtrare e recuperare i libri in base a dei parametri in input (DI ESEMPIO)
     */
    public List<BookModel> getAllFilter(String titleBook, BigDecimal price) {
        List<BookModel> bookModelList = new ArrayList<>();
        /*
         * Filtra i libri in base ai criteri specificati (titolo e prezzo).
         * Se titleBook o price sono null, vengono considerati come non specificati, e tutti i libri corrisponderanno a quel criterio.
         */
        for (BookModel bookModel : bookModels) {
            boolean checkTitle = titleBook == null || titleBook.isEmpty() || bookModel.getTitle().equalsIgnoreCase(titleBook);
            boolean checkPrice = price == null || bookModel.getPrice().compareTo(price) == 0;

            // Se entrambi le condizioni sono
            if (checkTitle && checkPrice) {
                bookModelList.add(bookModel);
            }
        }

        return bookModelList;
    }

    /*
     * Metodo Get per filtrare e recuperare i libri in base ai parametri in input all'interno di un oggetto di filtro (DI ESEMPIO)
     */
    public List<BookModel> getAllFilteredObject(BookFilter bookFilter) {
        List<BookModel> filteredBooks = new ArrayList<>();

        for (BookModel book : bookModels) {
            if (matchesFilter(book, bookFilter)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    /*
     * Metodo Post per aggiungere un libro (DI ESEMPIO)
     */
    public BookModel add(BookModel bookModel) {

        // Controllo di esistenza del libro nel nostro db statico
        if (isDuplicateBook(bookModel)) {
            System.out.println("E' gia esistente il libro: " + bookModel.getTitle());
            return bookModel;
        }

        // Conferma in console, dell'aggiunta del libro richiesto
        System.out.println("E' stato inserito il libro: " + bookModel.getTitle());

        return bookModel;
    }

    /*
     * Metodo Post per modificare un libro (DI ESEMPIO)
     */
    public BookModel edit(UUID bookId, BookModel bookModel) {
        // Controlla se esiste un libro con quell'id specifico
        for (BookModel bookModelDb: bookModels) {
            if (bookModelDb.getBookId().equals(bookId)) {
                // Se esiste viene modificato il titolo, con la richiesta inviata
                System.out.println("Il libro " + bookModelDb.getTitle() + " è stato modificato in: " + bookModel.getTitle());
                return bookModel;
            }
        }
        // Altrimenti si invia il messaggio di non riuscita della modifica
        System.out.println("Non è stato possibile trovare il libro: " + bookModel.getTitle());
        return null;

    }

    /*
     * Metodo Post per eliminare un libro (DI ESEMPIO)
     */
    public Boolean remove(UUID bookId) {

        // Controlla se esiste un libro con quell'id specifico
        for (BookModel bookModelDb: bookModels) {
            if (bookModelDb.getBookId().equals(bookId)) {
                // Se esiste viene cancellato il titolo, con la richiesta inviata
                System.out.println("Il libro " + bookModelDb.getTitle() + " è stato cancellato");
                return true;
            }
        }
        System.out.println("Non è stato possibile trovare il libro con questo id: " + bookId.toString());
        return false;

    }

    /*
     * Metodo privato per controllare se esista lo stesso libro nel nostro DB statico
     */
    private Boolean isDuplicateBook(BookModel bookModel) {
        for (BookModel existingBook : bookModels) {
            // Controlla che esista o meno un libro con stesso titolo e stesso autore
            if (existingBook.getTitle().equalsIgnoreCase(bookModel.getTitle())
                    && existingBook.getAuthor().getName().equalsIgnoreCase(bookModel.getAuthor().getName())
                    && existingBook.getAuthor().getSurname().equalsIgnoreCase(bookModel.getAuthor().getSurname())) {
                return true; // Il libro è già presente
            }
        }
        return false; // Nessun libro duplicato trovato
    }

    private Boolean matchesFilter(BookModel book, BookFilter filter) {
        // Logica del filtraggio in base ai valori inseriti nel filtro in input
        boolean titleMatch = filter.getTitle() == null || book.getTitle().equalsIgnoreCase(filter.getTitle());
        boolean authorNameMatch = filter.getAuthorName() == null || book.getAuthor().getName().equalsIgnoreCase(filter.getAuthorName());
        boolean priceMatch = filter.getPrice() == null || book.getPrice().compareTo(filter.getPrice()) == 0;
        boolean categoryMatch = filter.getCategory() == null || book.getCategory().equalsIgnoreCase(filter.getCategory());

        // Restituisce true se tutte le condizioni sono soddisfatte
        return titleMatch && authorNameMatch && priceMatch && categoryMatch;
    }

}