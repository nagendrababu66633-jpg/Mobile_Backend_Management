package org.example.orderservice.controller;

import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.entity.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(request);

        return ResponseEntity.ok(order);
    }
}