package net.targul.adservice.entity.ad;

import lombok.Builder;
import lombok.Data;

import net.targul.adservice.entity.category.Category;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(collection = "ads")
public class Ad {

    @Id
    private String id;

    @Indexed(unique = true)
    private String shortId;

    @Builder.Default
    private AdStatus status = AdStatus.PENDING;

    private String title;

    private String slug;

    private String description;

    private Double price;

    @DBRef
    private Category category;

    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;
}