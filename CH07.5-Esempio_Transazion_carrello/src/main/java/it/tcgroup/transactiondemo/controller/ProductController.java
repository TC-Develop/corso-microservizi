package it.tcgroup.transactiondemo.controller;

import it.tcgroup.transactiondemo.controller.payload.request.ProductRequest;
import it.tcgroup.transactiondemo.controller.payload.response.ProductResponse;
import it.tcgroup.transactiondemo.model.Product;
import it.tcgroup.transactiondemo.service.ProductService;
import it.tcgroup.transactiondemo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping(path = "")
    public ResponseEntity<ProductResponse> add(
            @RequestBody ProductRequest productRequest
    ){

        Product product = Converter.requestToModel(productRequest);
        product = productService.add(product);
        return ResponseEntity.ok(Converter.modelToResponse(product));
    }


    @GetMapping(path = "")
    public ResponseEntity<List<ProductResponse>> get(){

        List<ProductResponse> productResponseList = new ArrayList<>();

        List<Product> productList =  productService.get();

        for(Product product : productList){
            productResponseList.add(Converter.modelToResponse(product));
        }

        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(Converter.modelToResponse(productService.getById(id)));
    }

}
