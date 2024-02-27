package it.tcgroup.transactiondemo.controller.payload.response;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponse implements Serializable {
    private UUID id;
    private String name;
    private BigDecimal price;

}
