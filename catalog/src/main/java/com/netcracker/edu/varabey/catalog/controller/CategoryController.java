package com.netcracker.edu.varabey.catalog.controller;

import com.netcracker.edu.varabey.catalog.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.catalog.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.service.CategoryService;
import com.netcracker.edu.varabey.catalog.validation.CategoryValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final Transformer<Category, CategoryDTO> categoryTransformer;
    private final CategoryService categoryService;
    private final CategoryValidator categoryValidator;

    public CategoryController(CategoryService categoryService, Transformer<Category, CategoryDTO> categoryTransformer, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.categoryTransformer = categoryTransformer;
        this.categoryValidator = categoryValidator;
    }

    @GetMapping("/{input}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findCategory(@PathVariable("input") String input) {
        Category category = categoryValidator.checkFound(categoryService.find(input), "Category with identifier " + input + " was not found.");
        return categoryTransformer.toDto(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO saveCategory(@RequestBody CategoryDTO dto) {
        Category category = categoryTransformer.toEntity(dto);
        category = categoryService.save(category);
        return categoryTransformer.toDto(category);
    }

    @PostMapping("/saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoryDTO> saveAllCategories(@RequestBody List<CategoryDTO> dtoList) {
        return dtoList.stream()
                .map(this::saveCategory)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO updateCategoryName(@PathVariable("id") Long id, @RequestBody CategoryDTO dto) {
        Category category = categoryTransformer.toEntity(dto);
        category.setId(id);
        category = categoryService.updateName(category);
        return categoryTransformer.toDto(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}