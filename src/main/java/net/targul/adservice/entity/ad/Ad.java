package net.targul.adservice.entity.ad;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@Document(collection = "ads")
public class Ad {
    @Id
    private String id;
    @Builder.Default
    private AdStatus status = AdStatus.PENDING;
    private String title;
    private String description;
    private Double price;
    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();
    @Builder.Default
    private List<String> categoryIds = new ArrayList<>();
}