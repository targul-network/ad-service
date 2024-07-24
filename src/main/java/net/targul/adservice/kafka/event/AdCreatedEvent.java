package net.targul.adservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AdCreatedEvent {
    private String id;
    private String title;
    private String description;
    private double price;
    private List<String> categories;
}