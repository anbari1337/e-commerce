package com.aanbari.productservice.entity;


import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String tag;
    private String description;
    private Long price;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

}
