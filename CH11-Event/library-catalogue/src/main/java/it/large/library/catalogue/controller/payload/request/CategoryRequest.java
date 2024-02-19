package it.large.library.catalogue.controller.payload.request;

import it.large.library.catalogue.entity.type.CategoryTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryRequest implements Serializable {

    private UUID categoryId;

    private String name;

    private String description;

    private CategoryTypeEnum type;

}
