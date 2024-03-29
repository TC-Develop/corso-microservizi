package it.large.library.sales.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Setter
@Getter
@Document
public class BookDocument {

    private UUID bookId;

    private String title;

    private Double price;

}