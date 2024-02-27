package it.tcgroup.transactiondemo.repository;

import it.tcgroup.transactiondemo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {



}
