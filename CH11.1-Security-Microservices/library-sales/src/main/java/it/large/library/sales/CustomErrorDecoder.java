package it.large.library.sales;

import feign.Response;
import feign.codec.ErrorDecoder;
import it.large.library.sales.exception.NotFoundException;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            // Personalizza l'eccezione per risposte 404
            return new NotFoundException("Risorsa non trovata");
        }

        // Per altri codici di stato, restituisci un'eccezione predefinita di Feign
        return new ErrorDecoder.Default().decode(s, response);
    }
}