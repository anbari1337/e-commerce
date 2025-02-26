package com.aanbari.productservice.controller;

import com.aanbari.productservice.dto.ProductDto;
import com.aanbari.productservice.entity.Product;
import com.aanbari.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto){

        Product newProduct =productService.saveProduct(productDto);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public Product getProductInfo(@PathVariable String id) throws Exception{
        return productService.getProductById(id);
    }
}
