package com.aanbari.inventoryservice.controller;

import com.aanbari.inventoryservice.entity.Inventory;
import com.aanbari.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {
    InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/{productId}")
    public ResponseEntity<Inventory> saveInventory(@PathVariable String productId){
        Inventory inventory =  inventoryService.saveInventory(productId);
        return ResponseEntity.ok(inventory);
    }




}
