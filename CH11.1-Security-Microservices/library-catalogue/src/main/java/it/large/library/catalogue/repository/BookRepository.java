package it.large.library.catalogue.repository;

import it.large.library.catalogue.controller.payload.filter.BookFilter;
import it.large.library.catalogue.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/*
 * L'interfaccia JpaRepository è un'interfaccia generica che fornisce metodi per eseguire operazioni di base su un'entità,
 *  come la persistenza, il recupero, l'eliminazione, ecc.
 * Estendendo JpaRepository, BookRepository eredita una serie di metodi pronti all'uso per interagire con il database per l'entità BookEntity.
 * Specificando nei campi <Table, PK> tabella e chiave primaria, così da poter gestire la CRUD su tale entità.
 */
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    // Metodo che restituisce i libri in base ai filtri in input.
    // Questo metodo sfrutta la scrittura JPQL di Hibernate
    @Query("SELECT b FROM BookEntity b WHERE " +
            "b.title =:titleBook OR " +
            "b.price =:price ")
    List<BookEntity> getByTitleOrPrice(String titleBook, BigDecimal price);

    // Metodo che restituisce i libri in base ai filtri in input.
    // Questo metodo sfrutta la scrittura SQL nativa, con il parametro nativeQuery = true
    @Query(value = "SELECT * FROM catalogue_books b WHERE " +
            "b.title =:titleBook OR " +
            "b.price =:price "
            , nativeQuery = true)
    List<BookEntity> getByTitleOrPriceNativeQuery(String titleBook, BigDecimal price);


    // Questa query JPQL è progettata per filtrare i libri in base ai criteri specificati nel filtro (BookFilter)
    /*
     * Specifica che stai scrivendo una query JPQL per selezionare oggetti BookEntity dalla tabella dei libri (BookEntity b).
        L'uso di DISTINCT garantisce che ogni risultato sia unico.
        "LEFT JOIN b.categories c":

        Esegue una left join con la tabella delle categorie (b.categories), associando ogni libro a tutte le categorie corrispondenti.
        L'alias c viene utilizzato per fare riferimento alle categorie nella clausola WHERE successiva.
        "LEFT JOIN AuthorEntity a ON a = b.author WHERE " +:

        Esegue una left join con la tabella degli autori (AuthorEntity a) utilizzando l'associazione tra libro e autore (b.author).
        L'uso di ON a = b.author specifica la condizione di join.
        L'alias a viene utilizzato per fare riferimento agli autori nella clausola WHERE successiva.
        "(:#{#filterParam.title} IS NULL OR b.title = :#{#filterParam.title}) " +:

        La clausola WHERE inizia con un controllo sulla colonna title del libro.
        Se filterParam.title è nullo, il controllo viene ignorato; altrimenti, verifica se il titolo del libro corrisponde a filterParam.title.
        "AND (:#{#filterParam.authorName} IS NULL OR a.name = :#{#filterParam.authorName}) " +:

        Aggiunge una condizione alla clausola WHERE per verificare se il nome dell'autore corrisponde a filterParam.authorName.
        Se filterParam.authorName è nullo, il controllo viene ignorato.
        "AND (:#{#filterParam.price} IS NULL OR b.price = :#{#filterParam.price}) " +:

        Aggiunge una condizione alla clausola WHERE per verificare se il prezzo del libro corrisponde a filterParam.price.
        Se filterParam.price è nullo, il controllo viene ignorato.
        "AND (:#{#filterParam.category} IS NULL OR c.type = :#{#filterParam.category}) "):

        Aggiunge una condizione alla clausola WHERE per verificare se il tipo di categoria corrisponde a filterParam.category.
        Se filterParam.category è nullo, il controllo viene ignorato.
        Utilizza l'alias c per fare riferimento alle categorie.
     */
    @Query("SELECT DISTINCT b FROM BookEntity b " +
            "LEFT JOIN b.categories c " +
            "LEFT JOIN AuthorEntity a ON a = b.author WHERE " +
            "(:#{#filterParam.title} IS NULL OR b.title = :#{#filterParam.title}) " +
            "AND (:#{#filterParam.authorName} IS NULL OR a.name = :#{#filterParam.authorName}) " +
            "AND (:#{#filterParam.price} IS NULL OR b.price = :#{#filterParam.price}) " +
            "AND (:#{#filterParam.category} IS NULL OR c.type = :#{#filterParam.category}) ")
    List<BookEntity> filter(@Param("filterParam") BookFilter bookFilter);

}