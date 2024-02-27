package it.tcgroup.transactiondemo.controller.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderResponse {

    private UUID id;
    private String status;
    private String username;

}
