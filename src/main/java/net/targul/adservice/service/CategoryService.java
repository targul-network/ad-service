package net.targul.adservice.service;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;

public interface CategoryService {

    CategoryDto createCategory(CategoryRequest request);
}