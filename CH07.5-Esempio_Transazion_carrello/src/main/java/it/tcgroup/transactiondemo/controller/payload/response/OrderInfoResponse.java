package it.tcgroup.transactiondemo.controller.payload.response;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderInfoResponse implements Serializable {

    private UUID productId;
    private UUID orderId;
    private Integer quantity;
    private BigDecimal price;

}
