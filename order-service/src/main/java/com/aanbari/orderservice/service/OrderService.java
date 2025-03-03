package com.aanbari.orderservice.service;

import com.aanbari.orderservice.config.Constants;
import com.aanbari.orderservice.dto.OrderRequest;
import com.aanbari.orderservice.entity.Order;
import com.aanbari.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrder(Integer orderId){
        return orderRepository.findById(orderId).orElse(null);
    }
    public Order saveOrder(OrderRequest order) {
        return orderRepository.save(Order
                .builder()
                        .price(order.getPrice())
                        .userId(order.getUserId())
                        .productTag(order.getProductTag())
                        .status(Constants.PENDING)
                .build());
    }

    public void updateOrderStatus(Integer orderId, String status, Map<String, Boolean> productAvailability) {
        Order order = getOrder(orderId);
        if(order !=null){
            order.setStatus(status);
            order.setProductAvailability(productAvailability);
            orderRepository.save(order);
        }
    }

}
