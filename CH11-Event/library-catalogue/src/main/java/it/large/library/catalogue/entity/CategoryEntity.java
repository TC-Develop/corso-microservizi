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

    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum type;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<BookEntity> books;

}
