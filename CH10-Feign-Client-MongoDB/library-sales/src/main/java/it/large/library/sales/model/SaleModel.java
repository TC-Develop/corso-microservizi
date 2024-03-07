package it.large.library.sales.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class SaleModel {

    private UUID saleId;

    private UUID clientId;

    private Instant purchaseDate;

    private Double amount;

    private List<BookModel> books;

}