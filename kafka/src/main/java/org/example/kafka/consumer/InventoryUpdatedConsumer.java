package org.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryUpdatedConsumer {

    @KafkaListener(
            topics = "inventory.updated",
            groupId = "inventory-test-group"
    )
    public void consume(String message) {
        System.out.println("###############################");
        System.out.println("INVENTORY CONSUMER WORKING");
        System.out.println("MESSAGE = " + message);
        System.out.println("###############################");
    }
}