package net.targul.adservice.dto.ad;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdDto {

    Long pid;
    String status;
    String title;
    String slug;
    String description;
    Double price;
    List<String> imageUrls;
    List<String> categoryIds;
    LocalDateTime createdAt;
}