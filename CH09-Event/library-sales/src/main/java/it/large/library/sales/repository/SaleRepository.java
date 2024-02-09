package it.large.library.sales.repository;

import it.large.library.sales.document.SaleDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

// MongoRepository, come JpaRepository, è responsabile di gestire la CRUD sul documento indicato, con la sua chiave primaria (il tipo).
// Nel nostro caso abbiamo SaleDocument come Dpcument e UUID come tipo di chiave primaria.
public interface SaleRepository extends MongoRepository<SaleDocument, UUID> {


    // Query nativa mongo rispettiva a quella SQL: "SELECT * FROM SaleDocument sa WHERE sa.client_id = :clientId"
    @Query("{clientId: ?0}")
    List<SaleDocument> getSalesByClientId(UUID clientId);

}