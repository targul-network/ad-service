package net.targul.adservice.domain.ad;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import net.targul.adservice.domain.Category;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AdStatus status = AdStatus.PENDING;

    private String title;
    private String slug;
    private String description;
    private Double price;

    @ManyToMany
    @JoinTable(
            name = "ad_categories",
            joinColumns = @JoinColumn(name = "ad_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ElementCollection
    @CollectionTable(
            name = "ad_image_urls",
            joinColumns = @JoinColumn(name = "ad_id")
    )
    @Column(name = "url")
    private List<String> imageUrls;

    @Timestamp
    private LocalDateTime createdAt;
}