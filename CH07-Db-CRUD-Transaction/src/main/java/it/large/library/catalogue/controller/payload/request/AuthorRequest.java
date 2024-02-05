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

    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PutValidation.class})
    private UUID authorId;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private Date birthday;

    @NotNull
    private String birthCity;

    private Date deathDay;

}
