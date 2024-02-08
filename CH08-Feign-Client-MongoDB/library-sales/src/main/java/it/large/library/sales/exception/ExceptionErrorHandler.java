package it.large.library.sales.exception;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Questo è un esempio di @RestControllerAdvice in Spring,
 * che viene utilizzato per gestire globalmente le eccezioni in tutti i controller del progetto.
 */


// @RestControllerAdvice: Questa annotazione indica che la classe è un componente Spring responsabile di gestire le eccezioni nei controller.
// È una versione specializzata di @ControllerAdvice specifica per i controller RESTful.
@RestControllerAdvice
@CommonsLog
public class ExceptionErrorHandler {

    /*
     * Questo metodo viene chiamato quando si verifica un'eccezione di tipo RuntimeException o una delle sue sottoclassi nei controller.
     * Restituirà una risposta HTTP con un corpo di tipo Object.
     * Il metodo accetta un oggetto Throwable come parametro, che rappresenta l'eccezione che è stata lanciata.
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> errorHandler(Throwable exception) {
        // log.error(exception): utilizza il logger di @CommonsLog per registrare l'eccezione nel livello di errore del log.
        log.error(exception);
        exception.printStackTrace();
        // Creazione di una risposta personalizzata
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(exception.getMessage());

        /*
         * Determinazione del codice di stato HTTP:
         * Viene determinato il codice di stato HTTP in base al tipo di eccezione. Ad esempio:
         * Se l'eccezione è di tipo NotFoundException (NOSTRA ECCEZIONE PERSONALIZZATA), viene restituito HttpStatus.NOT_FOUND.
         * Se l'eccezione è di tipo AlreadyExistsException (NOSTRA ECCEZIONE PERSONALIZZATA), viene restituito HttpStatus.BAD_REQUEST.
         * Altrimenti, viene restituito HttpStatus.INTERNAL_SERVER_ERROR.
         */
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof NotFoundException) {
            code = HttpStatus.NOT_FOUND;
        } else if (exception instanceof AlreadyExistsException) {
        code = HttpStatus.BAD_REQUEST;
        }

        // Viene creata e restituita un'istanza di ResponseEntity contenente l'oggetto ExceptionResponse e il codice di stato HTTP determinato in base al tipo di eccezione.
        return new ResponseEntity<>(exceptionResponse, code);
    }

}