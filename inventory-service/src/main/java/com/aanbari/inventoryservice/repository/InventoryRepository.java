package com.aanbari.inventoryservice.repository;

import com.aanbari.inventoryservice.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {


    Inventory findByProductId(String productId);
}
