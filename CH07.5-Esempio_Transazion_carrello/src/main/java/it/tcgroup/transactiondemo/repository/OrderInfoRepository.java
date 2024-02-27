package it.tcgroup.transactiondemo.repository;

import it.tcgroup.transactiondemo.entity.OrderInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfoEntity, OrderInfoEntity.PrimaryKey> {

}
