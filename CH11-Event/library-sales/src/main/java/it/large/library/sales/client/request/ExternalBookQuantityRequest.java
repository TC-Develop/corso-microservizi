package it.large.library.sales.client.request;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ExternalBookQuantityRequest implements Serializable {

    private UUID bookId;

    private Integer quantity;

}