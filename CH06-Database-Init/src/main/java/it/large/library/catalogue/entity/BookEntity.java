package it.large.library.catalogue.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/*
 * BookEntity è una classe di entità JPA (Java Persistence API)
 * che rappresenta un libro nel contesto di un'applicazione che fa uso di un database relazionale.
 */

@Getter
@Setter
@NoArgsConstructor
// @Entity Indica che questa classe è un'entità JPA e deve essere gestita dal framework JPA.
// La classe BookEntity sarà mappata a una tabella nel database.
@Entity
// @Table Specifica il nome della tabella nel database a cui la classe è mappata.
// Nel caso presente, la tabella nel database si chiamerà "catalogue_books".
@Table(name = "catalogue_books")
public class BookEntity {

    // @Id Indica che il campo annotato è la chiave primaria dell'entità.
    @Id
    // @Column Configura la colonna corrispondente al campo nel database.
    // name = "book_id" specifica il nome della colonna nel database.
    // columnDefinition = "uuid" specifica il tipo di colonna nel database.
    @Column(name = "book_id", columnDefinition = "uuid")
    // @GeneratedValue Configura come viene generato il valore della chiave primaria.
    // GenerationType.AUTO indica che il valore viene generato automaticamente dal sistema.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookId;

    private String title;

    // @ManyToOne Indica una relazione many-to-one tra BookEntity e AuthorEntity.
    // Un libro può avere un solo autore, ma un autore può avere più libri.
    @ManyToOne
    // @JoinColumn Configura la colonna che rappresenta la chiave esterna per la relazione many-to-one.
    // name = "author_id" specifica il nome della colonna nel database.
    // columnDefinition = "uuid" specifica il tipo di colonna nel database.
    // referencedColumnName = "author_id" specifica la colonna nella tabella degli autori a cui fa riferimento.
    @JoinColumn(name = "author_id", columnDefinition = "uuid", referencedColumnName = "author_id")
    private AuthorEntity author;

    private BigDecimal price;

    // @ManyToMany Indica una relazione many-to-many tra BookEntity e CategoryEntity.
    // Un libro può appartenere a più categorie e una categoria può avere più libri.
    @ManyToMany
    // @JoinTable Configura la tabella di join per la relazione many-to-many.
    // name = "book_category" specifica il nome della tabella di join nel database.
    // joinColumns specifica le colonne della tabella di join riferite all'entità corrente (BookEntity).
    // inverseJoinColumns specifica le colonne della tabella di join riferite all'altra entità (CategoryEntity).
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    )
    private List<CategoryEntity> categories;

}