package it.tcgroup.transactiondemo.service;

import it.tcgroup.transactiondemo.entity.ProductEntity;
import it.tcgroup.transactiondemo.exception.NotFoundException;
import it.tcgroup.transactiondemo.model.Product;
import it.tcgroup.transactiondemo.repository.ProductRepository;
import it.tcgroup.transactiondemo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public Product add(Product product){
        ProductEntity productEntity = Converter.modelToEntity(product);
        productEntity = productRepository.save(productEntity);
        return Converter.entityToModel(productEntity);
    }

    public Product getById(UUID id){

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return Converter.entityToModel(productEntity);
    }

    public List<Product> get(){
        List<Product> productEntityList = productRepository.findAll().stream().map(Converter::entityToModel).toList();
        return productEntityList;
    }
}
