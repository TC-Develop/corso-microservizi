package it.tcgroup.transactiondemo.controller.payload.request;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderInfoRequest implements Serializable {

    private UUID productId;
    private UUID orderId;
    private Integer quantity;
    private BigDecimal price;

}
