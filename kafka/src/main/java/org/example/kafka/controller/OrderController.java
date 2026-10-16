package org.example.kafka.controller;

import org.example.kafka.entity.Order;
import org.example.kafka.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/kafka/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long userId,
            @RequestParam BigDecimal totalAmount) {

        Order order = orderService.createOrder(
                userId,
                totalAmount
        );

        return ResponseEntity.ok(order);
    }
}