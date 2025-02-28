package com.aanbari.inventoryservice.controller;

import com.aanbari.inventoryservice.dto.ProductRequest;
import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
