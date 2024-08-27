package net.targul.adservice.controller;

import jakarta.validation.Valid;
import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.service.impl.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<CategoryDto>> getSubcategories(@PathVariable String categoryId) {
        List<CategoryDto> subcategories = categoryService.getSubcategoriesByParentCategoryId(categoryId);

        // if no subcategories found - return code 204 "No content"
        if(subcategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryDto>> getRootCategories() {
        List<CategoryDto> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(rootCategories);
    }

    @GetMapping("/{categoryId}/breadcrumbs")
    public ResponseEntity<List<CategoryDto>> getBreadcrumbsByCategoryId(@PathVariable String categoryId) {
        List<CategoryDto> breadcrumbs = categoryService.getBreadcrumbsByCategoryId(categoryId);
        return ResponseEntity.ok(breadcrumbs);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }
}