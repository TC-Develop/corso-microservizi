package it.large.library.catalogue.controller;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.controller.payload.group.PutValidation;
import it.large.library.catalogue.controller.payload.request.BookRequest;
import it.large.library.catalogue.controller.payload.response.BookResponse;
import it.large.library.catalogue.model.BookModel;
import it.large.library.catalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static it.large.library.catalogue.utils.ConverterConfig.*;

/*
 * Questo Controller da adesso sarà solamente di esempio.
 * Ci spostiamo su BookController.
 */

@RestController // Indica che questa classe è un controller Spring e che ogni metodo gestisce automaticamente la conversione dell'output in formato JSON.
@RequestMapping("catalogue/api/v1/book") // Specifica il percorso di base per tutte le richieste gestite da questo controller.
public class BookController {


    //Indica che il campo private BookService bookService deve essere iniettato automaticamente dal contesto di Spring.
    // BookService è preso in carico dal framework Spring per la gestione della logica di business legata ai libri.
    @Autowired
    private BookService bookService;


    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
     * @param bookId
     * @return
     */
    @GetMapping(path = "/{book_id}")
    public ResponseEntity<BookResponse> getById(
            // Specifica il nome del parametro nella stringa di query dell'URL. In questo caso, si aspetta un parametro con il nome "book_id".
            @PathVariable(name = "book_id") UUID bookId
    ) {
        // Si invoca il metodo del service di BookService getBook, per recuperare il libro fittizio. Che in questo caso è una stringa.
        BookModel bookModel = bookService.getBook(bookId);
        BookResponse bookResponse = converterBookModelToBookResponse(bookModel);

        // Restituisce una risposta HTTP con status 200 (OK) e il corpo della risposta contiene l'oggetto bookResponse.
        // La classe ResponseEntity consente di personalizzare la risposta HTTP, inclusi lo status e gli header.
        return ResponseEntity.ok(bookResponse);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller, con vari RequestParam.
     * @param titleBook
     * @param price
     * @return
     */
    @GetMapping(path = "")
    public ResponseEntity<List<BookResponse>> getAll(
            // @RequestParam(name = "title_book") sta specificando che il valore del parametro "title_book" dalla richiesta HTTP,
            // deve essere assegnato alla variabile titleBook nel tuo metodo del controller.
            // Stessa cosa vale per @RequestParam(name = "price") con BigDecimal price.
            // required = false indica che non dobbiamo inviare necessariamente il campo.
            @RequestParam(name = "title_book", required = false) String titleBook,
            @RequestParam(name = "price", required = false) BigDecimal price
    ) {
        List<BookModel> bookModelList = bookService.getAllFilter(titleBook, price);
        List<BookResponse> bookResponseList = converterBookModelListToBookResponseList(bookModelList);

        return ResponseEntity.ok(bookResponseList);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller, con un ModelAttribute in richiesta HTTP.
     * @param bookFilter
     * @return
     */
    @GetMapping(path = "/filter")
    public ResponseEntity<List<BookResponse>> getAllFiltered(
            // L'annotazione @ModelAttribute viene utilizzata per legare i parametri della richiesta a un oggetto BookFilter in questo caso.
            // L'annotazione @ModelAttribute genera tanti RequestParam quanti sono i campi dell'oggetto, bookFilter in questo caso.
            @ModelAttribute BookFilter bookFilter
    ) {
        List<BookModel> bookModelList = bookService.getAllFilteredObject(bookFilter);
        List<BookResponse> bookResponseList = converterBookModelListToBookResponseList(bookModelList);

        return ResponseEntity.ok(bookResponseList);
    }


    /**
     *  Indica che questo metodo risponde a richieste HTTP di tipo POST al percorso base specificato per il controller
     * @param bookRequest
     * @return
     */
    @PostMapping(path = "")
    public ResponseEntity<BookResponse> add(
            // L'annotazione @Validated in Spring viene utilizzata per abilitare la validazione degli argomenti dei metodi o dei parametri della richiesta all'interno dei controller.
            // La validazione verrà comunque eseguita automaticamente prima di entrare nel metodo
            @Validated @RequestBody BookRequest bookRequest // Il corpo della richiesta è mappato al parametro @RequestBody BookRequest bookRequest.
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
     * @param bookId
     * @param bookRequest
     * @return
     */
    @PutMapping(path = "/{book_id}")
    public ResponseEntity<BookResponse> edit(
            // Specifica il nome del parametro nella stringa di query dell'URL. In questo caso, si aspetta un parametro con il nome "book_id".
            @PathVariable(name = "book_id") UUID bookId,
            // L'annotazione @Validated in Spring viene utilizzata per abilitare la validazione degli argomenti dei metodi o dei parametri della richiesta all'interno dei controller.
            // La validazione verrà comunque eseguita automaticamente prima di entrare nel metodo, in base a dove è specificata la classe di Validazione specifica.
            // In questo caso la classe di validazione è PutValidation.class
            @Validated(PutValidation.class) @RequestBody BookRequest bookRequest //  Il corpo della richiesta è mappato al parametro @RequestBody BookRequest bookRequest.
    ) {
        BookModel bookModel = converterBookRequestToBookModel(bookRequest);
        bookModel = bookService.edit(bookId, bookModel);
        BookResponse bookResponse = converterBookModelToBookResponse(bookModel);

        return ResponseEntity.ok(bookResponse);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo DELETE al percorso base specificato per il controller
     * @param bookId
     * @return
     */
    @DeleteMapping(path = "/{book_id}")
    public ResponseEntity<Boolean> remove(
            @PathVariable(name = "book_id") UUID bookId
    ) {
        Boolean success = bookService.remove(bookId);

        return ResponseEntity.ok(success);
    }

}