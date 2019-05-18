package com.netcracker.edu.varabey.controller;

import com.netcracker.edu.varabey.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.controller.dto.transformer.Transformer;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.netcracker.edu.varabey.controller.util.RestPreconditions.checkFound;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final Transformer<Category, CategoryDTO> categoryTransformer;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, Transformer<Category, CategoryDTO> categoryTransformer) {
        this.categoryService = categoryService;
        this.categoryTransformer = categoryTransformer;
    }

    @GetMapping("/{input}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findCategory(@PathVariable("input") String input) {
        Category category = checkFound(categoryService.find(input));
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