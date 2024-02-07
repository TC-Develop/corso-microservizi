package it.large.library.sales.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/*
 * In MongoDB, un "document" è il formato di dati primario utilizzato per la memorizzazione e la gestione dei dati.
 * Un documento in MongoDB è una rappresentazione in formato JSON (JavaScript Object Notation) dei dati, ed è costituito da coppie chiave-valore.
 * I documenti sono organizzati in collezioni, che sono simili ai concetti di tabelle nei database relazionali.
 */

@Getter
@Setter
// @Document: Indica che la classe è una classe di dominio che deve essere persistita in MongoDB come un documento.
// collation = "sales": Specifica il nome della raccolta MongoDB a cui la classe è mappata. In MongoDB, una raccolta è equivalente a una tabella in un database relazionale. In questo caso, il nome della raccolta è "sales".
// value = "sales": È un alias per collation e specifica anche il nome della raccolta MongoDB. Quindi, collation e value sono intercambiabili in questo contesto.
@Document(collation = "sales", value = "sales")
public class SaleDocument {

    // Chiave primaria del document
    @Id
    private UUID saleId;

    private UUID clientId;

    private Timestamp purchaseDate;

    private BigDecimal amount;

    private List<BookDocument> books;

}