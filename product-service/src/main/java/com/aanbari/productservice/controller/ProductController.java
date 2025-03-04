package com.aanbari.productservice.controller;

import com.aanbari.productservice.dto.ProductDto;
import com.aanbari.productservice.dto.ProductEvent;
import com.aanbari.productservice.entity.Product;
import com.aanbari.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;


    public ProductController(ProductService productService, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.productService = productService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {

        Product newProduct = productService.saveProduct(productDto);
        kafkaTemplate.send("new-product-topic", ProductEvent.builder().productTag(newProduct.getTag()).build());
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy){
        return productService.getProductsList(page, size, sortBy);
    }

    @GetMapping("/product/{id}")
    public Product getProductInfo(@PathVariable Integer id) throws Exception {
        return productService.getProductById(id);
    }
}
