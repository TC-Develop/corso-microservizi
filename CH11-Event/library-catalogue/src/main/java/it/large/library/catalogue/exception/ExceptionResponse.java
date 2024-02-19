package it.large.library.catalogue.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/*
 * Classe custom per gestire il messaggio dell'errore in una response e mostrarlo in risposta.
 */
@Data
public class ExceptionResponse implements Serializable {

    @JsonProperty("message")
    private String message;

}