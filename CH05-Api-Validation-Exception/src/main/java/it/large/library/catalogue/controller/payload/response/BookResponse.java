package it.large.library.catalogue.controller.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookResponse implements Serializable {

    private UUID bookId;

    private String title;

    private AuthorResponse author;

    private BigDecimal price;

}
