package it.tcgroup.transactiondemo.repository;

import it.tcgroup.transactiondemo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
