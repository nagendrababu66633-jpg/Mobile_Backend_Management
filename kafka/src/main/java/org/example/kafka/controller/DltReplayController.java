package org.example.kafka.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/dlt")
public class DltReplayController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DltReplayController(
            KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/replay")
    public String replay(@RequestBody String message) {

        kafkaTemplate.send(
                "order.created",
                message
        );

        return "Message replayed to order.created";
    }
}