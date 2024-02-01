package it.large.library.catalogue.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryModel {

    private UUID categoryId;

    private String name;

    private String description;

    private String type;

}
