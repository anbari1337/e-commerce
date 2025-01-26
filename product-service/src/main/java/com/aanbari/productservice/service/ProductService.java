package com.aanbari.productservice.service;

import com.aanbari.productservice.dto.ProductDto;
import com.aanbari.productservice.entity.Product;
import com.aanbari.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product saveProduct(ProductDto product) {
        return productRepository.save(
                Product
                        .builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .tag(product.getTag())
                        .build()
        );
    }

    public Product getProductById(String id){
        return  productRepository.findById(id).orElseThrow(); // TODO ProductNotFoundException
    }

}
