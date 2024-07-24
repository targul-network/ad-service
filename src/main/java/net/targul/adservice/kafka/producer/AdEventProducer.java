package net.targul.adservice.kafka.producer;

import net.targul.adservice.kafka.event.AdCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdEventProducer {

    private static final String TOPIC = "ad-created-events";

    @Autowired
    private KafkaTemplate<String, AdCreatedEvent> kafkaTemplate;

    public void sendAdCreatedEvent(AdCreatedEvent adCreatedEvent) {
        kafkaTemplate.send(TOPIC, adCreatedEvent);
    }
}

