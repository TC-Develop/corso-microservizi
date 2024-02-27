package it.tcgroup.transactiondemo.repository;

import it.tcgroup.transactiondemo.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, CartEntity.PrimaryKey> {

    List<CartEntity> findCartEntitiesByPrimaryKeyUsername(String username);

    Optional<CartEntity> findCartEntitiesByPrimaryKeyUsernameAndProductId(String username, UUID productId);

}
