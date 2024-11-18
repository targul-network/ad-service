package net.targul.adservice.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String slug;
    private String imageUrl;
    private Category parentCategory;
}