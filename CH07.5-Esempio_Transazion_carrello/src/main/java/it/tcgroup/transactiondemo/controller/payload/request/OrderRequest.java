package it.tcgroup.transactiondemo.controller.payload.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequest {

    private UUID id;
    private String status;
    private String username;

}
