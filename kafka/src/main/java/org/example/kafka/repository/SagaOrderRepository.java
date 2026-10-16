package org.example.kafka.repository;

import org.example.kafka.entity.SagaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaOrderRepository
        extends JpaRepository<SagaOrder, Long> {
}
