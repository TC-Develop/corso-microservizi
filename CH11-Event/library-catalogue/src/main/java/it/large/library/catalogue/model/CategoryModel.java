package it.large.library.catalogue.model;

import it.large.library.catalogue.entity.type.CategoryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryModel {

    private UUID categoryId;

    private String name;

    private String description;

    private CategoryTypeEnum type;

    private List<BookModel> books;

}
