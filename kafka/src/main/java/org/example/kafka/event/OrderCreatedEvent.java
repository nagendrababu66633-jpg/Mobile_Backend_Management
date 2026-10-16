package org.example.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderCreatedEvent {

    private String eventId;
    private String eventType;
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime occurredAt;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(
            String eventId,
            String eventType,
            Long orderId,
            Long userId,
            BigDecimal totalAmount,
            LocalDateTime occurredAt) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.occurredAt = occurredAt;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}