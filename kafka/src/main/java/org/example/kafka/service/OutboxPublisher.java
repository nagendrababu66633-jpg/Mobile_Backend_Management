package org.example.kafka.service;

import org.example.kafka.entity.OutboxEvent;
import org.example.kafka.repository.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxEventRepository outboxEventRepository,
            KafkaTemplate<String, String> kafkaTemplate) {

        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelayString = "${outbox.publisher.fixed-delay:5000}")
    public void publishPendingEvents() {

        List<OutboxEvent> events =
                outboxEventRepository.findByStatusOrderByCreatedAtAsc("PENDING");

        for (OutboxEvent event : events) {

            try {
                System.out.println("====================================");
                System.out.println("OUTBOX PUBLISHER");
                System.out.println("Event ID: " + event.getEventId());
                System.out.println("Topic: " + event.getTopic());
                System.out.println("Status: " + event.getStatus());

                kafkaTemplate.send(
                        event.getTopic(),
                        event.getEventId(),
                        event.getPayload()
                ).get();

                event.setStatus("PUBLISHED");
                event.setPublishedAt(LocalDateTime.now());

                outboxEventRepository.save(event);

                System.out.println("EVENT PUBLISHED SUCCESSFULLY");
                System.out.println("Event ID: " + event.getEventId());
                System.out.println("====================================");

            } catch (Exception e) {

                event.setRetryCount(event.getRetryCount() + 1);

                outboxEventRepository.save(event);

                System.out.println("====================================");
                System.out.println("KAFKA PUBLISH FAILED");
                System.out.println("Event ID: " + event.getEventId());
                System.out.println("Retry Count: " + event.getRetryCount());
                System.out.println("Error: " + e.getMessage());
                System.out.println("Event remains PENDING");
                System.out.println("====================================");
            }
        }
    }
}