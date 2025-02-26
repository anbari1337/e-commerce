package com.aanbari.orderservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private List<String> productId;
    private Integer userId;
    private Double price;
}
