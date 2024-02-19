package it.large.library.sales.controller.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookResponse implements Serializable {

    private UUID bookId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

}