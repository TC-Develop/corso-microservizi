package it.large.library.sales.controller.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class SaleResponse implements Serializable {

    private UUID saleId;

    private UUID clientId;

    private Timestamp purchaseDate;

    private Double amount;

    private List<BookResponse> books;

}