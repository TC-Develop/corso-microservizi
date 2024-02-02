package it.large.library.catalogue.repository;

import it.large.library.catalogue.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 * L'interfaccia JpaRepository è un'interfaccia generica che fornisce metodi per eseguire operazioni di base su un'entità,
 *  come la persistenza, il recupero, l'eliminazione, ecc.
 * Estendendo JpaRepository, BookRepository eredita una serie di metodi pronti all'uso per interagire con il database per l'entità BookEntity.
 * Specificando nei campi <Table, PK> tabella e chiave primaria, così da poter gestire la CRUD su tale entità.
 */
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
}
