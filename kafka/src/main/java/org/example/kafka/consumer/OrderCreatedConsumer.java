
package org.example.kafka.consumer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class OrderCreatedConsumer {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public OrderCreatedConsumer(
            JdbcTemplate jdbcTemplate,
            ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;

        System.out.println("OrderCreatedConsumer bean created");
    }

    @KafkaListener(
            topics = "order.created",
            groupId = "order-created-consumer"
    )
    public void consume(String message) {

        System.out.println("KAFKA MESSAGE RECEIVED: " + message);

        try {
            JsonNode event = objectMapper.readTree(message);

            JsonNode eventIdNode = event.get("eventId");

            if (eventIdNode == null
                    || eventIdNode.isNull()
                    || eventIdNode.asText().isBlank()) {
                System.err.println("ERROR: eventId is missing");
                return;
            }

            String eventId = eventIdNode.asText();

            int inserted = jdbcTemplate.update(
                    "INSERT INTO processed_events (event_id) VALUES (?) "
                            + "ON CONFLICT (event_id) DO NOTHING",
                    eventId
            );

            if (inserted == 0) {
                System.out.println(
                        "DUPLICATE EVENT SKIPPED: " + eventId);
                return;
            }

            System.out.println("EVENT PROCESSED: " + eventId);

            // Add order business logic here.

        } catch (Exception e) {
            System.err.println(
                    "ERROR PROCESSING EVENT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
