package it.large.library.catalogue.controller;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.controller.payload.group.PutValidation;
import it.large.library.catalogue.controller.payload.request.BookQuantityRequest;
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

@RestController
@RequestMapping("catalogue/api/v1/book")
public class BookController {


    @Autowired
    private BookService bookService;


    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller
     * @param bookId
     * @return
     */
    @GetMapping(path = "/{book_id}")
    public ResponseEntity<BookResponse> getById(
            @PathVariable(name = "book_id") UUID bookId
    ) {
        BookModel bookModel = bookService.getBook(bookId);
        BookResponse bookResponse = converterBookModelToBookResponse(bookModel);

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
            @RequestParam(name = "title_book", required = false) String titleBook,
            @RequestParam(name = "price", required = false) BigDecimal price
    ) {
        List<BookModel> bookModelList = bookService.getAllFilter(titleBook, price);
        List<BookResponse> bookResponseList = convertBookModelListToBookResponseList(bookModelList);

        return ResponseEntity.ok(bookResponseList);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo GET al percorso base specificato per il controller, con un ModelAttribute in richiesta HTTP.
     * @param bookFilter
     * @return
     */
    @GetMapping(path = "/filter")
    public ResponseEntity<List<BookResponse>> getAllFiltered(
            @ModelAttribute BookFilter bookFilter
    ) {
        List<BookModel> bookModelList = bookService.getAllFilteredObject(bookFilter);
        List<BookResponse> bookResponseList = convertBookModelListToBookResponseList(bookModelList);

        return ResponseEntity.ok(bookResponseList);
    }


    /**
     *  Indica che questo metodo risponde a richieste HTTP di tipo POST al percorso base specificato per il controller
     * @param bookRequest
     * @return
     */
    @PostMapping(path = "")
    public ResponseEntity<BookResponse> add(
            @Validated @RequestBody BookRequest bookRequest
    ) {
        BookModel bookModel = converterBookRequestToBookModel(bookRequest);
        bookModel = bookService.add(bookModel);
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
            @PathVariable(name = "book_id") UUID bookId,
            @Validated(PutValidation.class) @RequestBody BookRequest bookRequest
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
        bookService.remove(bookId);

        return ResponseEntity.ok(Boolean.TRUE);
    }

    /**
     * Indica che questo metodo risponde a richieste HTTP di tipo PUT al percorso base specificato per il controller
     * Useremo questa rotta per decrementare le quantità di libri specifici
     * Verrà invocata dal microservizio di Sales, al momento della vendita
     * @param bookQuantityRequestList
     * @return
     */
    @PutMapping(path = "/quantity")
    public ResponseEntity<Boolean> editQuantity(
            @Validated @RequestBody List<BookQuantityRequest> bookQuantityRequestList
    ) {

        bookService.editBookQuantity(bookQuantityRequestList);

        return ResponseEntity.ok(Boolean.TRUE);
    }

}