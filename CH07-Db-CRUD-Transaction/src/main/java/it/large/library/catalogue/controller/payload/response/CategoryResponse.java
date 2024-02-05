package it.large.library.catalogue.controller.payload.response;

import it.large.library.catalogue.entity.type.CategoryTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryResponse implements Serializable {

    private UUID categoryId;

    private String name;

    private String description;

    private CategoryTypeEnum type;

    private List<BookResponse> books;

}
