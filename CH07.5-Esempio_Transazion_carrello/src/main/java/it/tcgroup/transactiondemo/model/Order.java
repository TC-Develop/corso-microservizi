package it.tcgroup.transactiondemo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Order {

    private UUID id;
    private String status;
    private String username;

}
