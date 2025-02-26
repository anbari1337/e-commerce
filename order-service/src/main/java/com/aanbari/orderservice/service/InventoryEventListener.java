package com.aanbari.orderservice.service;

import com.aanbari.orderservice.config.Constants;
import com.aanbari.orderservice.dto.InventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class InventoryEventListener {

    private OrderService orderService;

    @Autowired
    public InventoryEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${spring.kafka.template.topic}", groupId = "${spring.kafka.group}",
            containerFactory = "kafkaListenerContainerFactory")
    public void checkProductAvailability(InventoryEvent event) {
        Map<String, Boolean> productAvailability = event.getProductAvailability();
        boolean allAvailable = true;
        for (String productId: productAvailability.keySet()){
            if(!productAvailability.get(productId)){
                orderService.updateOrderStatus(event.getOrderId(), Constants.FAILED, productAvailability);
                allAvailable = false;
                break;
            }
        }
        if(allAvailable){
            orderService.updateOrderStatus(event.getOrderId(), Constants.SUCCESS, productAvailability);
        }
    }


}
