package com.aanbari.inventoryservice.event;

import com.aanbari.inventoryservice.dto.ProductEvent;
import com.aanbari.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {

    private final InventoryService inventoryService;

    @Autowired
    public ProductEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @KafkaListener(topics = "new-product-topic", groupId = "${spring.kafka.group}",
            containerFactory = "newProductEventListenerFactory", errorHandler = "kafkaErrorHandler")
    public void addNewProductListener(ProductEvent event) {
        inventoryService.saveInventory(event.getProductTag());
    }
}
