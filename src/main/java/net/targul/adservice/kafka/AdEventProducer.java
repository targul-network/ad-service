package net.targul.adservice.kafka;

import net.targul.adservice.event.AdEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdEventProducer {
    private final KafkaTemplate<String, AdEvent> kafkaTemplate;

    public AdEventProducer(KafkaTemplate<String, AdEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAdEvent(AdEvent adEvent) {
        kafkaTemplate.send("ads", adEvent);
    }
}
