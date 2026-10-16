
package org.example.kafka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "saga_orders")
public class SagaOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    private String status;

    @Column(unique = true, nullable = false)
    private String sagaId;

    private String failureReason;

    public SagaOrder() {
    }

    public SagaOrder(Long productId, int quantity, String sagaId) {
        this.productId = productId;
        this.quantity = quantity;
        this.sagaId = sagaId;
        this.status = "PENDING";
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSagaId() {
        return sagaId;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
