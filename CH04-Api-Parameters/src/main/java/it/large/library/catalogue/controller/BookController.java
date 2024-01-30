package it.large.library.catalogue.controller;

import it.large.library.catalogue.controller.payload.request.BookRequest;
import it.large.library.catalogue.controller.payload.response.BookResponse;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static it.large.library.catalogue.utils.ConverterConfig.converterBookModelToBookResponse;
import static it.large.library.catalogue.utils.ConverterConfig.converterBookRequestToBookModel;

@RestController // Indica che questa classe è un controller Spring e che ogni metodo gestisce automaticamente la conversione dell'output in formato JSON.
@RequestMapping("catalogue/api/v1/book") // Specifica il percorso di base per tutte le richieste gestite da questo controller.
public class BookController {


    //Indica che il campo private BookService bookService deve essere iniettato automaticamente dal contesto di Spring.
    // BookService è preso in carico dal framework Spring per la gestione della logica di business legata ai libri.
    @Autowired
    private BookService bookService;


    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
     * @param titleBook
     * @return
     */
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


    /**
     *  Indica che questo metodo risponde a richieste HTTP di tipo POST al percorso base specificato per il controller
     * @param bookRequest
     * @return
     */
    @PostMapping(path = "")
    public ResponseEntity<BookResponse> add(
            @RequestBody BookRequest bookRequest //  Il corpo della richiesta è mappato al parametro @RequestBody BookRequest bookRequest.
    ) {
        // Converte l'oggetto BookRequest ricevuto dalla richiesta HTTP in un oggetto BookModel, facendo una mappatura manuale dei campi.
        BookModel bookModel = converterBookRequestToBookModel(bookRequest);
        // Invoca il metodo add del bookService per aggiungere il libro. Il risultato è il libro aggiunto rappresentato da un oggetto BookModel.
        bookModel = bookService.add(bookModel);
        // Converte l'oggetto BookModel risultante in un oggetto BookResponse. Anche qui, si tratta  di una conversione manuale dei campi.
        BookResponse bookResponse = converterBookModelToBookResponse(bookModel);

        return ResponseEntity.ok(bookResponse);
    }


    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo PUT al percorso base specificato per il controller
     * @param titleBook
     * @param bookRequest
     * @return
     */
    @PutMapping(path = "/{title_book}")
    public ResponseEntity<BookResponse> edit(
            // Specifica il nome del parametro nella stringa di query dell'URL. In questo caso, si aspetta un parametro con il nome "title_book".
            @PathVariable(name = "title_book") String titleBook,
            @RequestBody BookRequest bookRequest //  Il corpo della richiesta è mappato al parametro @RequestBody BookRequest bookRequest.
    ) {
        BookModel bookModel = converterBookRequestToBookModel(bookRequest);
        bookModel = bookService.edit(titleBook, bookModel);
        BookResponse bookResponse = converterBookModelToBookResponse(bookModel);

        return ResponseEntity.ok(bookResponse);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo DELETE al percorso base specificato per il controller
     * @param titleBook
     * @return
     */
    @DeleteMapping(path = "/{title_book}")
    public ResponseEntity<Boolean> remove(
            @PathVariable(name = "title_book") String titleBook
    ) {
        Boolean success = bookService.remove(titleBook);

        return ResponseEntity.ok(success);
    }

}