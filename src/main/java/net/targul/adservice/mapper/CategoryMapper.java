package net.targul.adservice.mapper;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.entity.category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }

    public CategoryDto toDto(Category category) {
        Category parentCategory = category.getParentCategory();
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .parentCategoryId(parentCategory != null ? parentCategory.getId() : null)
                .build();
    }
}