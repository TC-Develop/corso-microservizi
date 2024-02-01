package it.large.library.catalogue.controller.payload.request;

import it.large.library.catalogue.controller.payload.group.PostValidation;
import it.large.library.catalogue.controller.payload.group.PutValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookRequest implements Serializable {

    // Questa annotazione è parte delle annotazioni di validazione di Bean Validation.
    // Indica che il campo bookId deve essere nullo quando viene effettuata una validazione con il gruppo di validazione PostValidation, in metodo POST di un controller.
    // Non deve essere nullo in PostValidation, quindi nel metodo PUT di un controller.
    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PutValidation.class})
    private UUID bookId;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private String title;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private AuthorRequest author;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private BigDecimal price;

}