package it.large.library.sales.controller.payload.request;

import it.large.library.sales.controller.payload.group.PostValidation;
import it.large.library.sales.controller.payload.group.PutValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class BookRequest implements Serializable {

    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PutValidation.class})
    private UUID bookId;

    @NotNull
    private Integer quantity;

}