
package org.example.kafka.repository;

import org.example.kafka.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {
}
