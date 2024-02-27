package it.tcgroup.transactiondemo.controller.payload.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CartRequest {

    private String username;
    private Integer quantity;
    private UUID productId;

}
