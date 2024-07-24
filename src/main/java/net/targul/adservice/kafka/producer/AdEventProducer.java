package net.targul.adservice.kafka.producer;

import net.targul.adservice.kafka.event.AdCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdEventProducer {
    private static final String TOPIC = "ad-created-events";

    private final KafkaTemplate<String, AdCreatedEvent> kafkaTemplate;

    public AdEventProducer(KafkaTemplate<String, AdCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdCreatedEvent(AdCreatedEvent adCreatedEvent) {
        kafkaTemplate.send(TOPIC, adCreatedEvent);
    }
}

