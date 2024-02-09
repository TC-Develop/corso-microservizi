package it.large.library.catalogue.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catalogue_authors")
public class AuthorEntity {

    @Id
    @Column(name = "author_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID authorId;

    private String name;

    private String surname;

    private Date birthday;

    @Column(name = "birth_city")
    private String birthCity;

    @Column(name = "death_day")
    private Date deathDay;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<BookEntity> books;

}
