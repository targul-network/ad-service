package net.targul.adservice.service.impl;

import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.entity.category.Category;
import net.targul.adservice.exception.EntityNotFoundException;
import net.targul.adservice.mapper.CategoryMapper;
import net.targul.adservice.repository.CategoryRepository;
import net.targul.adservice.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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

        Category savedCategory = categoryRepository.save(categoryToSave);

        return categoryMapper.toDto(savedCategory);
    }
}