package com.aanbari.inventoryservice.service;

import com.aanbari.inventoryservice.dto.InventoryEvent;
import com.aanbari.inventoryservice.dto.OrderEvent;
import com.aanbari.inventoryservice.dto.ProductInventoryEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventListener {
    private InventoryService inventoryService;
    private KafkaTemplate<String, InventoryEvent> kafkaTemplate;
    private NewTopic topic;


    @Autowired
    public OrderEventListener(InventoryService inventoryService, KafkaTemplate<String, InventoryEvent> kafkaTemplate, NewTopic topic) {
        this.inventoryService = inventoryService;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @KafkaListener(topics = "${spring.kafka.template.topic}", groupId = "${spring.kafka.group}",
            containerFactory = "kafkaListenerContainerFactory")
    public void checkProductAvailability(OrderEvent event) {
        Map<String, Boolean> productAvailability = inventoryService.isProductAvailable(event.getProductId());
        kafkaTemplate.send(topic.name(),
                InventoryEvent
                        .builder()
                        .orderId(event.getOrderId())
                        .productAvailability(productAvailability)
                        .build());
    }

    @KafkaListener(topics = "${spring.kafka.topic.update-inventory}", groupId = "${spring.kafka.group}",
            containerFactory = "inventoryListenerFactory")
    public void updateInventory(ProductInventoryEvent event) {
        event.getProductQuantity().keySet().forEach(productId -> {
            inventoryService.updateInventory(productId, event.getProductQuantity().get(productId));
        });
    }

}
