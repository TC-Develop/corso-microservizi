package it.large.library.catalogue.controller.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryResponse implements Serializable {

    private UUID categoryId;

    private String name;

    private String description;

    private String type;

}
