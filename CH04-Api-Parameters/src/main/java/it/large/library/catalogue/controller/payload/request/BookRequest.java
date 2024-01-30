package it.large.library.catalogue.controller.payload.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookRequest implements Serializable {

    private UUID bookId;

    private String title;

    private AuthorRequest author;

    private BigDecimal price;

}