package net.targul.adservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ads")
@Getter
@Setter
public class Ad {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private List<Category> categories;
}
