package it.large.library.catalogue.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BookModel {

    private UUID bookId;

    private String title;

    private AuthorModel author;

    private BigDecimal price;

    private String category;

    public BookModel(UUID bookId, String title, AuthorModel author, BigDecimal price, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
    }
}