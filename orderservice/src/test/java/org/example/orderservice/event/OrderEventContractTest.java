
package org.example.orderservice.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderEventContractTest {

    @Test
    void eventTypeShouldMatchContract() {
        OrderEvent event = new OrderEvent();
        event.setEventType("order.created");

        assertEquals("order.created", event.getEventType());
    }

    @Test
    void schemaVersionShouldMatchContract() {
        OrderEvent event = new OrderEvent();
        event.setSchemaVersion("1");

        assertEquals("1", event.getSchemaVersion());
    }

    @Test
    void eventIdShouldBePresent() {
        OrderEvent event = new OrderEvent();
        event.setEventId("event-101");

        assertNotNull(event.getEventId());
        assertFalse(event.getEventId().isBlank());
    }

    @Test
    void correlationIdShouldBePresent() {
        OrderEvent event = new OrderEvent();
        event.setCorrelationId("correlation-101");

        assertNotNull(event.getCorrelationId());
        assertFalse(event.getCorrelationId().isBlank());
    }
}
