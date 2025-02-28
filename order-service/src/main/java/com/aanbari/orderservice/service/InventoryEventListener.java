package com.aanbari.orderservice.service;

import com.aanbari.orderservice.config.Constants;
import com.aanbari.orderservice.dto.InventoryEvent;
import com.aanbari.orderservice.dto.ProductInventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class InventoryEventListener {

    private OrderService orderService;
    private final KafkaTemplate<String, ProductInventoryEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.update-inventory}")
    private String updateInventoryTopic;


    @Autowired
    public InventoryEventListener(OrderService orderService, KafkaTemplate<String, ProductInventoryEvent> kafkaTemplate) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;

    }

    @KafkaListener(topics = "${spring.kafka.template.topic}", groupId = "${spring.kafka.group}",
            containerFactory = "inventoryEventListenerFactory")
    public void checkProductAvailability(InventoryEvent event) {
        Map<String, Boolean> productAvailability = event.getProductAvailability();
        boolean allAvailable = true;
        for (String productTag : productAvailability.keySet()) {
            if (!productAvailability.get(productTag)) {
                orderService.updateOrderStatus(event.getOrderId(), Constants.FAILED, productAvailability);
                allAvailable = false;
                break;
            }
        }
        if (allAvailable) {
            orderService.updateOrderStatus(event.getOrderId(), Constants.SUCCESS, productAvailability);
            // send message: update inventory
            Map<String, Integer> productQuantity = new HashMap<>();
            productAvailability.keySet().forEach(productTag -> {
                productQuantity.put(productTag, 1); // {product_tag: quantity}
            });
            kafkaTemplate.send(updateInventoryTopic,
                    ProductInventoryEvent
                            .builder()
                            .productQuantity(productQuantity)
                            .build());
        }
    }


}
