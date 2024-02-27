package it.tcgroup.transactiondemo.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Product implements Serializable {

    private UUID id;

    private String name;

    private BigDecimal price;



}
