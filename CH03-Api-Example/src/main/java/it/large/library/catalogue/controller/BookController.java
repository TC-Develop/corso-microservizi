package it.large.library.catalogue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica che questa classe è un controller Spring e che ogni metodo gestisce automaticamente la conversione dell'output in formato JSON.
@RequestMapping("catalogue/api/v1/book") // Specifica il percorso di base per tutte le richieste gestite da questo controller.
public class BookController {


    // Indica che questo metodo risponde a richieste HTTP di tipo POST al percorso base specificato per il controller
    @GetMapping(path = "/{title_book}")
    public ResponseEntity<String> getByName(
            // Specifica il nome del parametro nella stringa di query dell'URL. In questo caso, si aspetta un parametro con il nome "title_book".
            @PathVariable(name = "title_book") String titleBook
    ) {

        String bookTitle = "Questo è il titolo del libro richiesto: " + titleBook;

        // Restituisce una risposta HTTP con status 200 (OK) e il corpo della risposta contiene l'oggetto bookResponse.
        // La classe ResponseEntity consente di personalizzare la risposta HTTP, inclusi lo status e gli header.
        return ResponseEntity.ok(bookTitle);
    }

}