package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.validation.CategoryNotFoundException;
import com.netcracker.edu.varabey.service.validation.InvalidCategoryException;

interface CategoryService {

    /**
     * Saves a given category.
     * @param category new Category
     * @return saved category
     * @throws InvalidCategoryException if category is null || is detached || has invalid name
     */
    Category createCategory(Category category);

    /**
     * Retrieves a category by it's id.
     * @param id - must not be null.
     * @return found category or null.
     */
    Category findCategory(Long id);

    /**
     * Updates category's name.
     * @param category with updates
     * @return updated category
     * @throws InvalidCategoryException if category is new or if it has invalid name
     * @throws CategoryNotFoundException if category wasn't saved before.
     */
    Category updateCategory(Category category);

    /**
     * Deletes category by id
     * @param id - must not be null
     */
    void deleteCategory(Long id);
}
