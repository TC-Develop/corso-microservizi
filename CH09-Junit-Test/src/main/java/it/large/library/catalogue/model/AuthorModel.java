package it.large.library.catalogue.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AuthorModel {

    private UUID authorId;

    private String name;

    private String surname;

    private Date birthday;

    private String birthCity;

    private Date deathDay;

    private List<BookModel> books;

}