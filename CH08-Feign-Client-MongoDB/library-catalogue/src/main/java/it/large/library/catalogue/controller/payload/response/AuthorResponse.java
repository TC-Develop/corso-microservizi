package it.large.library.catalogue.controller.payload.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class AuthorResponse implements Serializable {

    private UUID authorId;

    private String name;

    private String surname;

    private Date birthday;

    private String birthCity;

    private Date deathDay;

    private List<BookResponse> books;

}