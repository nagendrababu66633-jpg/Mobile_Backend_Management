
package org.example.kafka.controller;

import org.example.kafka.entity.Inventory;
import org.example.kafka.entity.SagaOrder;
import org.example.kafka.service.SagaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/saga")
public class SagaController {

    private final SagaService sagaService;

    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    public record CreateOrderRequest(
            Long productId,
            int quantity,
            boolean simulatePaymentFailure) {
    }

    @PostMapping("/orders")
    public ResponseEntity<SagaOrder> createOrder(
            @RequestBody CreateOrderRequest request) {

        SagaOrder order = sagaService.createOrder(
                request.productId(),
                request.quantity(),
                request.simulatePaymentFailure()
        );

        if ("CANCELLED".equals(order.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(order);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(order);
    }

    @GetMapping("/orders/{orderId}")
    public SagaOrder getOrder(@PathVariable Long orderId) {
        return sagaService.getOrder(orderId);
    }

    @GetMapping("/inventory/{productId}")
    public Map<String, Object> getInventory(
            @PathVariable Long productId) {

        Inventory inventory = sagaService.getInventory(productId);

        return Map.of(
                "productId", inventory.getProductId(),
                "availableStock", inventory.getAvailableStock()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest().body(
                Map.of("error", exception.getMessage())
        );
    }
}
