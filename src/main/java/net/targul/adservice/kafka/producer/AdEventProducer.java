package net.targul.adservice.kafka.producer;

import net.targul.adservice.kafka.event.AdCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdEventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdEventProducer.class);
    private static final String TOPIC = "ad-created-events";

    private final KafkaTemplate<String, AdCreatedEvent> kafkaTemplate;

    public AdEventProducer(KafkaTemplate<String, AdCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdCreatedEvent(AdCreatedEvent adCreatedEvent) {
        LOGGER.info("EVENT HAS BEEN SENT!!!!!!!!!!!!!!");
        kafkaTemplate.send(TOPIC, adCreatedEvent);
    }
}

