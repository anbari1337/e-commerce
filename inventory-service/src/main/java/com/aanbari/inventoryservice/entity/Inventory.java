package com.aanbari.inventoryservice.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Setter
@Document
public class Inventory {

    @Id
    private String id;
    private String productTag;
    private Integer quantityInStock;
    private boolean isAvailable;
}
