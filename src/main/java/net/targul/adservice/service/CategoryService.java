package net.targul.adservice.service;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequest request);
    List<CategoryDto> getBreadcrumbsByCategoryId(Long categoryId);
    List<CategoryDto> getRootCategories();
    List<CategoryDto> getSubcategoriesByParentCategoryId(Long parentCategoryId);
}