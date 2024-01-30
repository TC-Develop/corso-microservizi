package it.large.library.catalogue.service;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    public String getBook(String titleBook) {
        // Libro disponibile
        String availableBook = "Harry Potter e l'ordine della fenice";

        // Semplice controllo se viene richimata la stringa del libro giusto, allora viene recuperato il libro.
        if (titleBook.equals(availableBook)) {
            return "Il titolo del libro: " + titleBook + " è presente nel catalogo!";
        }
        // Altrimenti il libro non viene recuperato
        return "Il titolo del libro: " + titleBook + " non è presente nel catalogo!";
    }

}