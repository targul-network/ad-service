package net.targul.adservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.domain.Category;
import net.targul.adservice.service.exception.EntityNotFoundException;
import net.targul.adservice.service.exception.UniqueValueException;
import net.targul.adservice.mapper.CategoryMapper;
import net.targul.adservice.repository.CategoryRepository;
import net.targul.adservice.service.CategoryService;
import net.targul.adservice.util.SlugUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SlugUtils slugUtils;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, SlugUtils slugUtils) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.slugUtils = slugUtils;
    }

    @Override
    public CategoryDto createCategory(CategoryRequest request) {
        Category categoryToSave = categoryMapper.toEntity(request);

        String parentCategoryId = request.getParentCategoryId();
        if (parentCategoryId != null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(parentCategoryId);

            if (optionalParentCategory.isEmpty()) {
                throw new EntityNotFoundException(
                        String.format("No parent category entity with ID: [%s] found.", parentCategoryId)
                );
            }
            categoryToSave.setParentCategory(optionalParentCategory.get());
        }

        // generating a slug from category name
        String nameSlug = slugUtils.generateSlug(request.getName());

        // checking the possibility of creating a unique slug from name
        if(categoryRepository.existsBySlug(nameSlug)) {
           throw new UniqueValueException(String.format("Category [slug: %s] already exists.", nameSlug));
        } else {
            categoryToSave.setSlug(nameSlug);
        }

        Category savedCategory = categoryRepository.save(categoryToSave);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getBreadcrumbsByCategoryId(String categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new EntityNotFoundException(String.format("Category [ID: %s] does not exist.", categoryId));
        }

        // filling breadcrumbs list
        List<Category> breadcrumbs = new ArrayList<>();
        Category currentCategory = optionalCategory.get();
        while (currentCategory != null) {
            breadcrumbs.add(currentCategory);
            currentCategory = currentCategory.getParentCategory();
        }

        // reversing breadcrumbs and mapping categories to dto
        return breadcrumbs.reversed().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<CategoryDto> getRootCategories() {
        List<Category> rootCategories = categoryRepository.findAllByParentCategoryIsNull();
        return rootCategories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public List<CategoryDto> getSubcategoriesByParentCategoryId(String parentCategoryId) {
        List<Category> subcategories = categoryRepository.findAllByParentCategoryId(parentCategoryId);
        return subcategories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}