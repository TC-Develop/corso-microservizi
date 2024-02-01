package it.large.library.catalogue.controller.payload.request;

import it.large.library.catalogue.controller.payload.group.PostValidation;
import it.large.library.catalogue.controller.payload.group.PutValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class AuthorRequest implements Serializable {

    // Questa annotazione è parte delle annotazioni di validazione di Bean Validation.
    // Indica che il campo bookId deve essere nullo quando viene effettuata una validazione con il gruppo di validazione PostValidation, in metodo POST di un controller.
    // Non deve essere nullo in PostValidation, quindi nel metodo PUT di un controller.
    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PutValidation.class})
    private UUID authorId;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private String name;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private String surname;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private Date birthday;

    // Non deve essere nullo in qualsiasi caso questo oggetto viene richiesto
    @NotNull
    private String birthCity;

    // Questo campo è facoltativo e quindi privo di validazione
    private Date deathDay;

}
