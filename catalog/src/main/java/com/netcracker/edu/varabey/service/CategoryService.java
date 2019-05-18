package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.validation.exceptions.CategoryNotFoundException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;

public interface CategoryService {

    /**
     * Saves a given category.
     * @param category new Category
     * @return saved category
     * @throws InvalidCategoryException if category is null || is detached || has invalid name
     */
    Category save(Category category);

    /**
     * Retrieves a category by it's id.
     * @param id - must not be null.
     * @return found category or null.
     */
    Category findById(Long id);

    /**
     * Retrieves a category by it's name.
     * @param name - must not be null.
     * @return found category or null.
     */
    Category findByName(String name);

    Category find(String input);

    /**
     * Retrieves a category by it's name.
     * @param name - must not be null.
     * @return found category or null.
     */
    Category getByName(String name);

    /**
     * Updates category's name.
     * @param category with updates
     * @return updated category
     * @throws InvalidCategoryException if category is new or if it has invalid name
     * @throws CategoryNotFoundException if category wasn't saved before.
     */
    Category updateName(Category category);

    /**
     * Deletes category by id
     * @param id - must not be null
     */
    void delete(Long id);
}
