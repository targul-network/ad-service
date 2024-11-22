package net.targul.adservice.dto.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {

    Long id;
    String name;
    String slug;
    String imageUrl;
    Long parentCategoryId;
}