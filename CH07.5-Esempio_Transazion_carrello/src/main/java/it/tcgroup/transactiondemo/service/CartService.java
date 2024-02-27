package it.tcgroup.transactiondemo.service;

import it.tcgroup.transactiondemo.entity.CartEntity;
import it.tcgroup.transactiondemo.entity.ProductEntity;
import it.tcgroup.transactiondemo.exception.NotFoundException;
import it.tcgroup.transactiondemo.model.Cart;
import it.tcgroup.transactiondemo.repository.CartRepository;
import it.tcgroup.transactiondemo.repository.ProductRepository;
import it.tcgroup.transactiondemo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart get(String username){
        List<CartEntity> cartEntities = cartRepository.findCartEntitiesByPrimaryKeyUsername(username);

        Cart cart = Converter.entityToModel(cartEntities.stream().findFirst().orElseThrow(() -> new NotFoundException()));

        for(CartEntity entity : cartEntities){
            cart.getProductInfos().add(new Cart.ProductInfo(entity.getProduct().getId(), entity.getQuantity()));
        }

        return cart;
    }

    public Cart add(String username, UUID prodId, Integer quantity){

        ProductEntity productEntity = productRepository.findById(prodId).orElseThrow(() -> new NotFoundException("Product Not Found " + prodId));

        Optional<CartEntity> optionalCartEntity  = cartRepository.findCartEntitiesByPrimaryKeyUsernameAndProductId(username, prodId);

        CartEntity cartEntity = optionalCartEntity.orElse(null);

        if(cartEntity==null){
            CartEntity.PrimaryKey primaryKey = new CartEntity.PrimaryKey();
            cartEntity = new CartEntity();

            primaryKey.setUsername(username);

            cartEntity.setPrimaryKey(primaryKey);
            cartEntity.setProduct(productEntity);
            cartEntity.setQuantity(quantity);

        }else {
            cartEntity.setQuantity(cartEntity.getQuantity() + quantity);
        }

        cartEntity = cartRepository.save(cartEntity);

        Cart cart = Converter.entityToModel(cartEntity);

        cart.getProductInfos().add(new Cart.ProductInfo(cartEntity.getProduct().getId(), cartEntity.getQuantity()));

        return cart;
    }

    public Cart delete(String username, UUID prodId, Integer quantity){

        Optional<CartEntity> cartEntityOptional = cartRepository.findCartEntitiesByPrimaryKeyUsernameAndProductId(username, prodId);

        CartEntity cartEntity = cartEntityOptional.orElseThrow(() -> new NotFoundException("Prodotto non presente in carrello"));

        if(cartEntity.getQuantity() - quantity >0){
            cartEntity.setQuantity(cartEntity.getQuantity()-quantity);
            cartEntity = cartRepository.save(cartEntity);
            Cart cart = Converter.entityToModel(cartEntity);
            cart.getProductInfos().add(new Cart.ProductInfo(cartEntity.getProduct().getId(), cartEntity.getQuantity()));
            return cart;
        }else{
            cartRepository.delete(cartEntity);
            return null;
        }
    }


}
