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
import java.util.Optional;

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
        Optional<Inventory> inventory1 = inventoryRepository.findByProductTag(productTag);
        if (inventory1.isEmpty()) {
            Inventory newInventory = Inventory.builder()
                    .productTag(productTag)
                    .quantityInStock(1)
                    .isAvailable(true)
                    .build();
            return inventoryRepository.save(newInventory);

        } else {
            // If not save new one
            Inventory inventory = inventory1.get();
            inventory.setQuantityInStock(inventory.getQuantityInStock() + 1);
            inventory.setAvailable(true);
            return inventoryRepository.save(inventory);
        }
    }

    public void updateInventoryQuantity(String productTag, int quantity, InventoryActionsEnum action) {
        Inventory inventory = inventoryRepository.findByProductTag(productTag).get();

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
            Inventory inventory = inventoryRepository.findByProductTag(id).get();
            productsAvailability.put(id, inventory.isAvailable());
        });

        return productsAvailability;
    }
}
