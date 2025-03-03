package com.aanbari.inventoryservice.event;

import com.aanbari.inventoryservice.constants.InventoryActionsEnum;
import com.aanbari.inventoryservice.dto.InventoryEvent;
import com.aanbari.inventoryservice.dto.OrderEvent;
import com.aanbari.inventoryservice.dto.ProductInventoryEvent;
import com.aanbari.inventoryservice.exception.InventoryNotFoundException;
import com.aanbari.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
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
            Map<String, Boolean> productAvailability = inventoryService.isProductAvailable(event.getProductTag());
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
    public void reduceProductQuantity(ProductInventoryEvent event) {
        event.getProductQuantity().keySet().forEach(productTag -> {
            inventoryService.updateInventoryQuantity(productTag,
                    event.getProductQuantity().get(productTag),
                    InventoryActionsEnum.REDUCE);
        });
    }


}
