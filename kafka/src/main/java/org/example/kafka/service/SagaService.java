
package org.example.kafka.service;

import org.example.kafka.entity.Inventory;
import org.example.kafka.entity.SagaOrder;
import org.example.kafka.repository.InventoryRepository;
import org.example.kafka.repository.SagaOrderRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class SagaService implements ApplicationRunner {

    private final SagaOrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SagaService(
            SagaOrderRepository orderRepository,
            InventoryRepository inventoryRepository,
            KafkaTemplate<String, String> kafkaTemplate) {

        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Add sample inventory only when it does not already exist.
        if (!inventoryRepository.existsById(1L)) {
            inventoryRepository.save(new Inventory(1L, 10));
        }

        if (!inventoryRepository.existsById(2L)) {
            inventoryRepository.save(new Inventory(2L, 5));
        }
    }

    @Transactional
    public SagaOrder createOrder(
            Long productId,
            int quantity,
            boolean simulatePaymentFailure) {

        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Product ID and positive quantity are required");
        }

        String sagaId = UUID.randomUUID().toString();

        // Step 1: Create order
        SagaOrder order = new SagaOrder(productId, quantity, sagaId);
        order = orderRepository.save(order);

        publishEvent(order, "ORDER_PENDING",
                "Order created and waiting for processing");

        // Step 2: Reserve inventory
        Inventory inventory = inventoryRepository.findById(productId)
                .orElse(null);

        if (inventory == null || inventory.getAvailableStock() < quantity) {
            order.setStatus("CANCELLED");
            order.setFailureReason("Insufficient inventory");
            orderRepository.save(order);

            publishEvent(order, "ORDER_CANCELLED",
                    "Order cancelled because inventory is unavailable");

            return order;
        }

        inventory.setAvailableStock(
                inventory.getAvailableStock() - quantity);

        inventoryRepository.save(inventory);

        publishEvent(order, "INVENTORY_RESERVED",
                "Inventory reserved successfully");

        // Step 3: Simulate downstream payment service
        if (simulatePaymentFailure) {

            order.setStatus("CANCELLED");
            order.setFailureReason("Payment authorization failed");
            orderRepository.save(order);

            publishEvent(order, "PAYMENT_FAILED",
                    "Payment authorization failed");

            // Compensation: restore the reserved inventory
            inventory.setAvailableStock(
                    inventory.getAvailableStock() + quantity);

            inventoryRepository.save(inventory);

            publishEvent(order, "INVENTORY_RELEASED",
                    "Reserved inventory released as compensation");

            publishEvent(order, "ORDER_CANCELLED",
                    "Order cancelled after payment failure");

            return order;
        }

        publishEvent(order, "PAYMENT_AUTHORIZED",
                "Payment authorized successfully");

        // Step 4: Confirm order
        order.setStatus("CONFIRMED");
        order.setFailureReason(null);
        orderRepository.save(order);

        publishEvent(order, "ORDER_CONFIRMED",
                "Order confirmed successfully");

        return order;
    }

    @Transactional(readOnly = true)
    public SagaOrder getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));
    }

    @Transactional(readOnly = true)
    public Inventory getInventory(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));
    }

    private void publishEvent(
            SagaOrder order,
            String eventType,
            String message) {

        // Simple JSON serialization; all string values here are controlled.
        String json = String.format(
                "{\"eventId\":\"%s\",\"sagaId\":\"%s\","
                        + "\"orderId\":%d,\"eventType\":\"%s\","
                        + "\"status\":\"%s\",\"message\":\"%s\","
                        + "\"occurredAt\":\"%s\"}",
                UUID.randomUUID(),
                order.getSagaId(),
                order.getId(),
                eventType,
                order.getStatus(),
                message,
                Instant.now()
        );

        kafkaTemplate.send("saga.events", order.getSagaId(), json);
    }
}
