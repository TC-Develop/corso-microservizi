package it.large.library.catalogue.entity;

import it.large.library.catalogue.entity.type.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catalogue_categories")
public class CategoryEntity {

    @Id
    @Column(name = "category_id", columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID categoryId;

    private String name;

    private String description;

    // Questa annotazione è utilizzata per mappare un campo di tipo enumerato
    // EnumType.STRING indica che il valore dell'enumerazione dovrebbe essere memorizzato come una stringa nel database.
    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum type;

    // @ManyToMany Questa annotazione gestisce la relazione many-to-many tra CategoryEntity e BookEntity.
    // mappedBy = "categories" specifica che questa relazione è mappata dalla proprietà categories nella classe BookEntity.
    // cascade = CascadeType.ALL specifica che tutte le operazioni di persistenza (ad esempio, aggiunta, rimozione) ->
    // effettuate su un'istanza di CategoryEntity si rifletteranno anche sulle relazioni BookEntity associate.
    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<BookEntity> books;

}
