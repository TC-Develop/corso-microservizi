package it.large.library.sales.model;

import lombok.Data;

import java.util.UUID;

@Data
public class BookModel {

    private UUID bookId;

    private String title;

    private Double price;

}