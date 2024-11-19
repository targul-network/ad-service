package net.targul.adservice.mapper;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .imageUrl(categoryRequest.getImageUrl())
                .build();
    }

    public CategoryDto toDto(Category category) {
        Category parentCategory = category.getParentCategory();
        return CategoryDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .slug(category.getSlug())
                .imageUrl(category.getImageUrl() != null ? category.getImageUrl() : null)
                .parentCategoryId(parentCategory != null ? parentCategory.getId().toString() : null)
                .build();
    }
}