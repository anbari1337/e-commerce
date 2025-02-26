package com.aanbari.orderservice.controller;

import com.aanbari.orderservice.dto.OrderEvent;
import com.aanbari.orderservice.dto.OrderRequest;
import com.aanbari.orderservice.entity.Order;
import com.aanbari.orderservice.service.OrderEventService;
import com.aanbari.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderService orderService;
    private OrderEventService orderEventService;


    @Autowired
    public OrderController(OrderEventService orderEventService, OrderService orderService) {
        this.orderEventService = orderEventService;
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }


    @PostMapping("/order")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderRequest order) {
        Order newOrder = orderService.saveOrder(order);

        // communicate with inventory service to check if the product is available
        orderEventService.sendOrderEvent(
                OrderEvent
                        .builder()
                        .productId(newOrder.getProductId())
                        .orderId(newOrder.getId())
                        .build());

        return ResponseEntity.ok(newOrder);
    }


}
