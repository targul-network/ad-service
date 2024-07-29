package net.targul.adservice.dto.ad;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdDto {
    private String id;
    private String title;
    private String description;
    private Double price;
}