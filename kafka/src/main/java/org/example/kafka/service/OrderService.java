package org.example.kafka.service;

import org.example.kafka.entity.Order;
import org.example.kafka.entity.OutboxEvent;
import org.example.kafka.repository.OrderRepository;
import org.example.kafka.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;

    public OrderService(OrderRepository orderRepository,
                        OutboxEventRepository outboxEventRepository) {

        this.orderRepository = orderRepository;
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional
    public Order createOrder(Long userId, BigDecimal totalAmount) {

        // 1. Create Order
        Order order = new Order(
                userId,
                totalAmount,
                "CREATED",
                LocalDateTime.now()
        );

        // 2. Save Order
        Order savedOrder = orderRepository.save(order);

        // 3. Generate unique event ID
        String eventId = UUID.randomUUID().toString();

        // 4. Create event JSON
        String payload = "{"
                + "\"eventId\":\"" + eventId + "\","
                + "\"eventType\":\"ORDER_CREATED\","
                + "\"orderId\":" + savedOrder.getId() + ","
                + "\"userId\":" + userId + ","
                + "\"totalAmount\":" + totalAmount + ","
                + "\"occurredAt\":\"" + LocalDateTime.now() + "\""
                + "}";

        // 5. Save event in Outbox table
        OutboxEvent outboxEvent = new OutboxEvent(
                eventId,
                "ORDER_CREATED",
                "order.created",
                payload,
                "PENDING",
                0,
                LocalDateTime.now()
        );

        outboxEventRepository.save(outboxEvent);

        System.out.println("====================================");
        System.out.println("ORDER CREATED");
        System.out.println("Order ID: " + savedOrder.getId());
        System.out.println("Event ID: " + eventId);
        System.out.println("OUTBOX EVENT SAVED");
        System.out.println("Status: PENDING");
        System.out.println("====================================");

        return savedOrder;
    }
}