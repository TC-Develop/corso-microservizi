package it.large.library.catalogue.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    public AuthorModel(UUID authorId, String name, String surname, Date birthday, String birthCity, Date deathDay) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.birthCity = birthCity;
        this.deathDay = deathDay;
    }
}