
package org.example.orderservice.service;

import org.example.orderservice.entity.OutboxEvent;
import org.example.orderservice.repository.OutboxEventRepository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OutboxPublisher {

    private final OutboxEventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxEventRepository repository,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEvent> events =
                repository.findTop100ByStatusOrderByCreatedAtAsc("NEW");

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(
                        event.getEventType(),
                        event.getEventId(),
                        event.getPayload()
                ).get(10, TimeUnit.SECONDS);

                event.setStatus("PUBLISHED");
                event.setPublishedAt(Instant.now());
                repository.save(event);

            } catch (Exception e) {
                // Keep the event NEW so a later scheduled attempt can retry.
                System.err.println(
                        "Could not publish event " + event.getEventId()
                                + ": " + e.getMessage());
            }
        }
    }
}
