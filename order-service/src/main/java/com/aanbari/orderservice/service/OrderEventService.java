package com.aanbari.orderservice.service;

import com.aanbari.orderservice.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEventService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final NewTopic topic;

    public OrderEventService(KafkaTemplate<String, OrderEvent> kafkaTemplate, NewTopic topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }


    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(topic.name(),
                OrderEvent.builder()
                        .productTag(orderEvent.getProductTag())
                        .orderId(orderEvent.getOrderId())
                        .build());
    }


}
