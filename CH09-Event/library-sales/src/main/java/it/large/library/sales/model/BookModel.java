package it.large.library.sales.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookModel {

    private UUID bookId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

}