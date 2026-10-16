
package org.example.kafka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    private Long productId;

    private int availableStock;

    public Inventory() {
    }

    public Inventory(Long productId, int availableStock) {
        this.productId = productId;
        this.availableStock = availableStock;
    }

    public Long getProductId() {
        return productId;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
