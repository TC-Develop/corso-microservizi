package it.large.library.catalogue.controller.payload.request;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class BookQuantityRequest implements Serializable {

    private UUID bookId;

    private Integer quantity;

}