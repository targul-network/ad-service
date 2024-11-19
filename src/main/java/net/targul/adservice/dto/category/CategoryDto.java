package net.targul.adservice.dto.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {

    String id;
    String name;
    String slug;
    String imageUrl;
    String parentCategoryId;
}