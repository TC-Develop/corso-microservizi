package it.large.library.catalogue.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BookModel {

    private UUID bookId;

    private String title;

    private AuthorModel author;

    private BigDecimal price;

    private Integer quantity;

    private List<CategoryModel> categories;

}