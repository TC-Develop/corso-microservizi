package it.tcgroup.transactiondemo.util;

import it.tcgroup.transactiondemo.controller.payload.request.CartRequest;
import it.tcgroup.transactiondemo.controller.payload.request.ProductRequest;
import it.tcgroup.transactiondemo.controller.payload.response.CartResponse;
import it.tcgroup.transactiondemo.controller.payload.response.ProductResponse;
import it.tcgroup.transactiondemo.entity.CartEntity;
import it.tcgroup.transactiondemo.entity.ProductEntity;
import it.tcgroup.transactiondemo.model.Cart;
import it.tcgroup.transactiondemo.model.Product;

public class Converter {

    public static Product requestToModel(ProductRequest productRequest){
        Product product = new Product();

        product.setId(productRequest.getId());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        return product;
    }


    public static ProductEntity modelToEntity(Product product){
        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());

        return productEntity;
    }

    public static Product entityToModel(ProductEntity productEntity){
        Product product = new Product();

        product.setId(productEntity.getId());
        product.setName(productEntity.getName());
        product.setPrice(productEntity.getPrice());

        return product;
    }


    public static ProductResponse modelToResponse(Product product){
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());

        return productResponse;
    }





    public static Cart requestToModel(CartRequest cartRequest){
        Cart cart = new Cart();
        cart.setUsername(cartRequest.getUsername());
        return cart;
    }

    public static CartEntity modelToEntity(Cart cart){
        CartEntity cartEntity = new CartEntity();
        CartEntity.PrimaryKey primaryKey = new CartEntity.PrimaryKey();

        primaryKey.setSeq(cart.getSeq());
        primaryKey.setUsername(cart.getUsername());
        cartEntity.setPrimaryKey(primaryKey);

        return cartEntity;
    }


    public static Cart entityToModel(CartEntity cartEntity){

        Cart cart = new Cart();
        cart.setSeq(cartEntity.getPrimaryKey().getSeq());
        cart.setUsername(cartEntity.getPrimaryKey().getUsername());

        return cart;
    }


    public static CartResponse modelToResponse(Cart cart){
        CartResponse cartResponse = new CartResponse();
        cartResponse.setSeq(cart.getSeq());
        cartResponse.setUsername(cart.getUsername());

        for(Cart.ProductInfo modelInfo : cart.getProductInfos()){
            cartResponse.getProductInfos().add(new CartResponse.ProductInfo(modelInfo.getProductId(), modelInfo.getQuantity()));
        }

        return cartResponse;
    }

}
