package net.targul.adservice.dto.ad;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdDto {
    private String id;
    private String status;
    private String title;
    private String description;
    private Double price;
    private List<String> imageUrls;
    private List<String> categoryIds;
}