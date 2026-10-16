package org.example.kafka.producer;

import org.example.kafka.event.InventoryUpdatedEvent;
import org.example.kafka.event.NotificationRequestedEvent;
import org.example.kafka.event.OrderCancelledEvent;
import org.example.kafka.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String ORDER_CREATED_TOPIC = "order.created";
    private static final String ORDER_CANCELLED_TOPIC = "order.cancelled";
    private static final String INVENTORY_UPDATED_TOPIC = "inventory.updated";
    private static final String NOTIFICATION_REQUESTED_TOPIC = "notification.requested";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    // ==========================================
    // 1. ORDER CREATED
    // ==========================================

    public void sendOrder(OrderCreatedEvent event) {

        String json = "{"
                + "\"eventId\":\"" + event.getEventId() + "\","
                + "\"eventType\":\"" + event.getEventType() + "\","
                + "\"orderId\":" + event.getOrderId() + ","
                + "\"userId\":" + event.getUserId() + ","
                + "\"totalAmount\":" + event.getTotalAmount() + ","
                + "\"occurredAt\":\"" + event.getOccurredAt() + "\""
                + "}";

        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                String.valueOf(event.getOrderId()),
                json
        );

        System.out.println("====================================");
        System.out.println("EVENT PUBLISHED");
        System.out.println("Topic: " + ORDER_CREATED_TOPIC);
        System.out.println("Message: " + json);
        System.out.println("====================================");
    }


    // ==========================================
    // 2. ORDER CANCELLED
    // ==========================================

    public void sendOrderCancelled(OrderCancelledEvent event) {

        String json = "{"
                + "\"eventId\":\"" + event.getEventId() + "\","
                + "\"eventType\":\"" + event.getEventType() + "\","
                + "\"orderId\":" + event.getOrderId() + ","
                + "\"userId\":" + event.getUserId() + ","
                + "\"reason\":\"" + event.getReason() + "\""
                + "}";

        kafkaTemplate.send(
                ORDER_CANCELLED_TOPIC,
                String.valueOf(event.getOrderId()),
                json
        );

        System.out.println("====================================");
        System.out.println("EVENT PUBLISHED");
        System.out.println("Topic: " + ORDER_CANCELLED_TOPIC);
        System.out.println("Message: " + json);
        System.out.println("====================================");
    }


    // ==========================================
    // 3. INVENTORY UPDATED
    // ==========================================

    public void sendInventoryUpdated(InventoryUpdatedEvent event) {

        String json = "{"
                + "\"eventId\":\"" + event.getEventId() + "\","
                + "\"eventType\":\"" + event.getEventType() + "\","
                + "\"productId\":" + event.getProductId() + ","
                + "\"quantity\":" + event.getQuantity() + ","
                + "\"operation\":\"" + event.getOperation() + "\""
                + "}";

        kafkaTemplate.send(
                INVENTORY_UPDATED_TOPIC,
                String.valueOf(event.getProductId()),
                json
        );

        System.out.println("====================================");
        System.out.println("EVENT PUBLISHED");
        System.out.println("Topic: " + INVENTORY_UPDATED_TOPIC);
        System.out.println("Message: " + json);
        System.out.println("====================================");
    }


    // ==========================================
    // 4. NOTIFICATION REQUESTED
    // ==========================================

    public void sendNotificationRequested(
            NotificationRequestedEvent event) {

        String json = "{"
                + "\"eventId\":\"" + event.getEventId() + "\","
                + "\"eventType\":\"" + event.getEventType() + "\","
                + "\"userId\":" + event.getUserId() + ","
                + "\"type\":\"" + event.getType() + "\","
                + "\"message\":\"" + event.getMessage() + "\""
                + "}";

        kafkaTemplate.send(
                NOTIFICATION_REQUESTED_TOPIC,
                String.valueOf(event.getUserId()),
                json
        );

        System.out.println("====================================");
        System.out.println("EVENT PUBLISHED");
        System.out.println("Topic: " + NOTIFICATION_REQUESTED_TOPIC);
        System.out.println("Message: " + json);
        System.out.println("====================================");
    }
}