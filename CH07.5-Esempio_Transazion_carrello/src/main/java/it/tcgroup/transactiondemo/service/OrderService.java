package it.tcgroup.transactiondemo.service;

import it.tcgroup.transactiondemo.entity.CartEntity;
import it.tcgroup.transactiondemo.entity.OrderEntity;
import it.tcgroup.transactiondemo.entity.OrderInfoEntity;
import it.tcgroup.transactiondemo.exception.NotFoundException;
import it.tcgroup.transactiondemo.model.Order;
import it.tcgroup.transactiondemo.repository.CartRepository;
import it.tcgroup.transactiondemo.repository.OrderInfoRepository;
import it.tcgroup.transactiondemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private CartRepository cartRepository;


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Order placeOrder(String username){
        boolean crash = false;

        List<CartEntity> cartEntityList = cartRepository.findCartEntitiesByPrimaryKeyUsername(username);

        if(cartEntityList.isEmpty()){
            throw new NotFoundException("Nessun carrello");
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUsername(username);
        orderEntity.setStatus("PLACED");

        orderEntity = orderRepository.save(orderEntity);

        if (crash){
            throw new RuntimeException();
        }

        for(CartEntity cartEntity : cartEntityList){
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();

            orderInfoEntity.setId(new OrderInfoEntity.PrimaryKey(cartEntity.getProduct().getId(),orderEntity.getId()));

            orderInfoEntity.setPrice(cartEntity.getProduct().getPrice());
            orderInfoEntity.setQuantity(cartEntity.getQuantity());
            orderInfoEntity.setOrder(orderEntity);
            orderInfoEntity.setProduct(cartEntity.getProduct());
            orderInfoRepository.save(orderInfoEntity);
            if (crash){
                throw new RuntimeException();
            }
        }

        if (crash){
            throw new RuntimeException();
        }

        cartRepository.deleteAll(cartEntityList);

        if (crash){
            throw new RuntimeException();
        }
        return null;
    }

    public List<Order> getOrder(String username){

        return null;
    }

}
