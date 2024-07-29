package net.targul.adservice.entity;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "ads")
public class Ad {

    @Id
    private String id;
    private String title;
    private String description;
    private Double price;
}