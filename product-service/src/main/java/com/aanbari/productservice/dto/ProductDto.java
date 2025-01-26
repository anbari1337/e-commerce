package com.aanbari.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDto {

    private String name;
    private String tag;
    private String description;
    private Long price;
}
