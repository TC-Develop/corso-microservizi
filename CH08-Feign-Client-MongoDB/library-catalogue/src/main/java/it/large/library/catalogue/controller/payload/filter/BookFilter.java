package it.large.library.catalogue.controller.payload.filter;

import it.large.library.catalogue.entity.type.CategoryTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BookFilter implements Serializable {

    private String title;

    private String authorName;

    private BigDecimal price;

    private CategoryTypeEnum category;

}