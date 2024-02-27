package it.tcgroup.transactiondemo.controller;

import it.tcgroup.transactiondemo.model.Order;
import it.tcgroup.transactiondemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderController> setOrder(
            @RequestHeader(name = "username") String username
    ){
        Order order = orderService.placeOrder(username);

        return ResponseEntity.ok(null);
    }



}
