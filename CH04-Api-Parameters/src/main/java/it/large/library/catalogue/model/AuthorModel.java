package it.large.library.catalogue.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AuthorModel {

    private UUID authorId;

    private String name;

    private String surname;

    private Date birthday;

    private String birthCity;

    private Date deathDay;

}