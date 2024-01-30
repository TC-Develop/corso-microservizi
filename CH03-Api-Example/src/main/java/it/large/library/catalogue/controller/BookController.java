package it.large.library.catalogue.controller;

import it.large.library.catalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Indica che questa classe è un controller Spring e che ogni metodo gestisce automaticamente la conversione dell'output in formato JSON.
@RequestMapping("catalogue/api/v1/book") // Specifica il percorso di base per tutte le richieste gestite da questo controller.
public class BookController {

    @Autowired
    private BookService bookService;


    // Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
    @GetMapping(path = "/{title_book}")
    public ResponseEntity<String> getByName(
            // Specifica il nome del parametro nella stringa di query dell'URL. In questo caso, si aspetta un parametro con il nome "title_book".
            @PathVariable(name = "title_book") String titleBook
    ) {

        // Si invoca il metodo del service di BookService getBook, per recuperare il libro fittizio. Che in questo caso è una stringa.
        String bookString = bookService.getBook(titleBook);

        // Restituisce una risposta HTTP con status 200 (OK) e il corpo della risposta contiene l'oggetto bookResponse.
        // La classe ResponseEntity consente di personalizzare la risposta HTTP, inclusi lo status e gli header.
        return ResponseEntity.ok(bookString);
    }

}