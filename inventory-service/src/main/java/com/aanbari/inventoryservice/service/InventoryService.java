package com.aanbari.inventoryservice.service;

import com.aanbari.inventoryservice.constants.InventoryActionsEnum;
import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
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

    public Inventory saveInventory(String productTag) {
        // If product already exist => increment quantityInStock
        Inventory inventory1 = inventoryRepository.findByProductTag(productTag);

        if (inventory1 == null) {
            Inventory newInventory = Inventory.builder()
                    .productTag(productTag)
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

    public void updateInventoryQuantity(String productTag, int quantity, InventoryActionsEnum action) {
        Inventory inventory = inventoryRepository.findByProductTag(productTag);

        int newQuantityInStock =
                action == InventoryActionsEnum.REDUCE ?
                        inventory.getQuantityInStock() - quantity :
                        inventory.getQuantityInStock() + quantity;
        if (newQuantityInStock > 0) {
            inventory.setQuantityInStock(newQuantityInStock);
        } else {
            inventory.setQuantityInStock(0);
            inventory.setAvailable(false);
        }
        inventoryRepository.save(inventory);
    }

    public Map<String, Boolean> isProductAvailable(List<String> productTag) {

        Map<String, Boolean> productsAvailability = new HashMap<>();
        productTag.forEach(id -> {
            Inventory inventory = inventoryRepository.findByProductTag(id);
            productsAvailability.put(id, inventory.isAvailable());
        });

        return productsAvailability;
    }
}
