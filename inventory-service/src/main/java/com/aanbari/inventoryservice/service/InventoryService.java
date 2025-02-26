package com.aanbari.inventoryservice.service;

import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InventoryService {
    InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory saveInventory(String productId) {
        // If product already exist => increment quantityInStock
        Inventory inventory1 = inventoryRepository.findByProductId(productId);

        if (inventory1 == null) {
            Inventory newInventory = Inventory.builder()
                    .productId(productId)
                    .quantityInStock(1)
                    .isAvailable(true)
                    .build();
        return inventoryRepository.save(newInventory);

        } else {
            // If not save new one
            inventory1.setQuantityInStock(inventory1.getQuantityInStock() + 1);
            inventory1.setAvailable(true);
        }
        return inventoryRepository.save(inventory1);
    }

    public Inventory updateInventory(String productId, int quantity){
        Inventory inventory = inventoryRepository.findByProductId(productId);

        int newQuantityInStock = inventory.getQuantityInStock() - quantity;
        if(newQuantityInStock > 0){
            inventory.setQuantityInStock(newQuantityInStock);
        }else {
            inventory.setQuantityInStock(0);
            inventory.setAvailable(false);
        }
        inventoryRepository.save(inventory);
        return inventory;
    }

    public Map<String, Boolean> isProductAvailable(List<String> productId){

        Map<String, Boolean> productsAvailability = new HashMap<>();
        productId.forEach(id -> {
            Inventory inventory = inventoryRepository.findByProductId(id);
                productsAvailability.put(id, inventory.isAvailable());
        });

        return productsAvailability;
    }
}
