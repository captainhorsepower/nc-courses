package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Category;

public interface CategoryService {

    /**
     * Saves a given category.
     * @param category new Category
     * @return saved category
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
     */
    Category updateName(Category category);

    /**
     * Deletes category by id
     * @param id - must not be null
     */
    void delete(Long id);
}
