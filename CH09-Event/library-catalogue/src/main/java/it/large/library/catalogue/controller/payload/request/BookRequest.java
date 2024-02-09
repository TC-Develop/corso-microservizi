package it.large.library.catalogue.controller.payload.request;

import it.large.library.catalogue.controller.payload.group.PostValidation;
import it.large.library.catalogue.controller.payload.group.PutValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class BookRequest implements Serializable {

    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PutValidation.class})
    private UUID bookId;

    @NotNull
    private String title;

    @NotNull
    private AuthorRequest author;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer quantity;

    @NotNull
    private Set<UUID> categoriesIds;

}