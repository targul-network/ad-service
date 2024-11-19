package net.targul.adservice.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.targul.adservice.dto.category.CategoryDto;
import net.targul.adservice.dto.category.CategoryRequest;
import net.targul.adservice.service.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@Validated
@Slf4j
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<CategoryDto>> getSubcategories(@PathVariable UUID categoryId) {
        List<CategoryDto> subcategories = categoryService.getSubcategoriesByParentCategoryId(categoryId);
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryDto>> getRootCategories() {
        List<CategoryDto> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(rootCategories);
    }

    @GetMapping("/{categoryId}/breadcrumbs")
    public ResponseEntity<List<CategoryDto>> getBreadcrumbsByCategoryId(@PathVariable UUID categoryId) {
        List<CategoryDto> breadcrumbs = categoryService.getBreadcrumbsByCategoryId(categoryId);
        return ResponseEntity.ok(breadcrumbs);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryRequest);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }
}