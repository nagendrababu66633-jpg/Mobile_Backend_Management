
package org.example.kafka.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/test")
public class KafkaTestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaTestController(
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/order-created")
    public String sendOrderCreatedEvent(@RequestBody String message) {

        kafkaTemplate.send("order.created", message)
                .whenComplete((result, error) -> {
                    if (error != null) {
                        System.err.println(
                                "KAFKA SEND FAILED: " + error.getMessage());
                    } else {
                        System.out.println(
                                "KAFKA SEND SUCCESS: topic="
                                        + result.getRecordMetadata().topic()
                                        + ", offset="
                                        + result.getRecordMetadata().offset());
                    }
                });

        return "Event submitted to Kafka";
    }
}
