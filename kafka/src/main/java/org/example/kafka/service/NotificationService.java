package org.example.kafka.service;

import org.example.kafka.entity.ProcessedEvent;
import org.example.kafka.repository.ProcessedEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final ProcessedEventRepository repository;

    public NotificationService(ProcessedEventRepository repository) {
        this.repository = repository;
    }

    public boolean alreadyProcessed(String eventId) {
        return repository.existsById(eventId);
    }

    public void markAsProcessed(String eventId, String eventType) {

        ProcessedEvent event = new ProcessedEvent(
                eventId,
                eventType,
                "PROCESSED",
                LocalDateTime.now()
        );

        repository.save(event);
    }

    public void sendNotification(
            String eventId,
            Long orderId,
            Long userId) {

        System.out.println("====================================");
        System.out.println("NOTIFICATION PROCESSING");
        System.out.println("Event ID: " + eventId);
        System.out.println("Order ID: " + orderId);
        System.out.println("User ID: " + userId);
        System.out.println("Notification sent successfully");
        System.out.println("====================================");
    }
}