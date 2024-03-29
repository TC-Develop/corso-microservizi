package it.large.library.sales.client.response;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ExternalBookResponse implements Serializable {

    private UUID bookId;

    private String title;

    private Double price;

}