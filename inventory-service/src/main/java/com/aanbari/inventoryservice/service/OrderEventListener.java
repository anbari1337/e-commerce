package com.aanbari.inventoryservice.service;

import com.aanbari.inventoryservice.dto.InventoryEvent;
import com.aanbari.inventoryservice.dto.OrderEvent;
import com.aanbari.inventoryservice.dto.ProductInventoryEvent;
import com.aanbari.inventoryservice.exception.InventoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class OrderEventListener {
    private final InventoryService inventoryService;
    private final KafkaTemplate<String, InventoryEvent> kafkaTemplate;
    private final NewTopic topic;


    @Autowired
    public OrderEventListener(InventoryService inventoryService, KafkaTemplate<String, InventoryEvent> kafkaTemplate, NewTopic topic) {
        this.inventoryService = inventoryService;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @KafkaListener(topics = "${spring.kafka.template.topic}", groupId = "${spring.kafka.group}",
            containerFactory = "orderEventListenerFactory", errorHandler = "kafkaErrorHandler")
    public void checkProductAvailability(OrderEvent event) {
        try {
            Map<String, Boolean> productAvailability = inventoryService.isProductAvailable(event.getProductId());
            kafkaTemplate.send(topic.name(),
                    InventoryEvent
                            .builder()
                            .orderId(event.getOrderId())
                            .productAvailability(productAvailability)
                            .build());
        } catch (Exception e) {
            throw new InventoryNotFoundException();
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.update-inventory}", groupId = "${spring.kafka.group}",
            containerFactory = "productInventoryEventListenerFactory", errorHandler = "kafkaErrorHandler")
    public void updateInventory(ProductInventoryEvent event) {
        event.getProductQuantity().keySet().forEach(productId -> {
            inventoryService.updateInventory(productId, event.getProductQuantity().get(productId));
        });
    }


}
