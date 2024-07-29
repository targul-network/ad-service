package net.targul.adservice.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import net.targul.adservice.dto.ad.AdDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdEventProducer {
    private static final String TOPIC = "ad-created-events";

    private final KafkaTemplate<String, AdDto> kafkaTemplate;

    public AdEventProducer(KafkaTemplate<String, AdDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdCreatedEvent(AdDto adCreatedEvent) {
        kafkaTemplate.send(TOPIC, adCreatedEvent);
    }
}