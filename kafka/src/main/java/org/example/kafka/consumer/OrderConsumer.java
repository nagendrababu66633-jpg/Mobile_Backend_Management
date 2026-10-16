package org.example.kafka.consumer;


import org.example.kafka.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(
            topics = "order.created",
            groupId = "kafka-demo-group"
    )
    public void consume(OrderCreatedEvent event) {

        System.out.println("====================================");
        System.out.println("ORDER CREATED EVENT RECEIVED");
        System.out.println("====================================");

        System.out.println("Event ID     : " + event.getEventId());
        System.out.println("Event Type   : " + event.getEventType());
        System.out.println("Order ID     : " + event.getOrderId());
        System.out.println("User ID      : " + event.getUserId());
        System.out.println("Total Amount : " + event.getTotalAmount());
        System.out.println("Occurred At  : " + event.getOccurredAt());

        System.out.println("====================================");
    }
}