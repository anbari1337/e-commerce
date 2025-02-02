package com.aanbari.inventoryservice.service;

import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }
        return inventoryRepository.save(inventory1);
    }
}
