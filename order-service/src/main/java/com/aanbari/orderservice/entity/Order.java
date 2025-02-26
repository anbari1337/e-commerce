package com.aanbari.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
@Entity
@Table(name = "t_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private List<String> productId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    private String status;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="product_availability")
    @MapKeyColumn(name="product_id")
    @Column(name="is_available")
    private Map<String, Boolean> productAvailability;

    private Double price;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
}
