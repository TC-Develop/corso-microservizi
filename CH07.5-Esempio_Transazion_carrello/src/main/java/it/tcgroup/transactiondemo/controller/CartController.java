package it.tcgroup.transactiondemo.controller;

import it.tcgroup.transactiondemo.controller.payload.response.CartResponse;
import it.tcgroup.transactiondemo.model.Cart;
import it.tcgroup.transactiondemo.service.CartService;
import it.tcgroup.transactiondemo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{prodId}/{quantity}")
    public ResponseEntity<CartResponse> post(
            @RequestHeader String username,
            @PathVariable(name = "prodId")UUID prodId,
            @PathVariable(name = "quantity") Integer quantity){

        Cart cart = cartService.add(username, prodId, quantity);

        return ResponseEntity.ok(Converter.modelToResponse(cart));

    }


    @GetMapping("")
    public ResponseEntity<CartResponse> get(
            @RequestHeader String username
    ){
        Cart cart = cartService.get(username);
        return ResponseEntity.ok(Converter.modelToResponse(cart));
    }


    @DeleteMapping("/{prodId}/{quantity}")
    public ResponseEntity<CartResponse>delete(
            @RequestHeader String username,
            @PathVariable(name = "prodId")UUID prodId,
            @PathVariable(name = "quantity") Integer quantity
    ){

        Cart cart = cartService.delete(username, prodId, quantity);

        if(cart==null){
            return new ResponseEntity<>(null);
        }
        return ResponseEntity.ok(Converter.modelToResponse(cart));
    }
}
