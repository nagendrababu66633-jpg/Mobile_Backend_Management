package org.example.kafka.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;

    private String eventType;

    private String topic;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status;

    private int retryCount;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    public OutboxEvent() {
    }

    public OutboxEvent(
            String eventId,
            String eventType,
            String topic,
            String payload,
            String status,
            int retryCount,
            LocalDateTime createdAt) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.topic = topic;
        this.payload = payload;
        this.status = status;
        this.retryCount = retryCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}