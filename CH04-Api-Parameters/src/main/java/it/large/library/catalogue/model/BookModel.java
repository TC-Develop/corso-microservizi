package it.large.library.catalogue.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookModel {

    private UUID bookId;

    private String title;

    private AuthorModel author;

    private BigDecimal price;

    private String category;

}