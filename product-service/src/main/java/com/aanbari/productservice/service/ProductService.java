package com.aanbari.productservice.service;

import com.aanbari.productservice.dto.ProductDto;
import com.aanbari.productservice.entity.Product;
import com.aanbari.productservice.exception.ProductNotFoundException;
import com.aanbari.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsList(int page, int size, String sortBy){

        return productRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy))).stream().toList();
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

    public Product getProductById(Integer id) throws Exception{
        return  productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with id="+id)); // TODO ProductNotFoundException
    }

}
