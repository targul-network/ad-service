package net.targul.adservice.dto.ad;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdDto {

    private String shortId;
    private String status;
    private String title;
    private String slug;
    private String description;
    private Double price;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
}