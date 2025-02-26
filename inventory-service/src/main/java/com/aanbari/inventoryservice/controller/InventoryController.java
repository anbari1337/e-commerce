package com.aanbari.inventoryservice.controller;

import com.aanbari.inventoryservice.dto.ProductRequest;
import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class InventoryController {
    InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventory")
    public ResponseEntity<Inventory> saveInventory(@RequestBody ProductRequest productRequest) {
        Inventory inventory = inventoryService.saveInventory(productRequest.getProductId());
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/inventory/{productId}/reduce")
    public ResponseEntity<Inventory> updateInventory(@PathVariable String productId,
                                                     @RequestParam(
                                                             required = false,
                                                             defaultValue = "1"
                                                     ) Integer quantity) {

        Inventory inventory = inventoryService.updateInventory(productId, quantity);
        return ResponseEntity.ok(inventory);
    }
}
