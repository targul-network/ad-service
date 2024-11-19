package net.targul.adservice.service;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryDto createCategory(CategoryRequest request);
    List<CategoryDto> getBreadcrumbsByCategoryId(UUID categoryId);
    List<CategoryDto> getRootCategories();
    List<CategoryDto> getSubcategoriesByParentCategoryId(UUID parentCategoryId);
}