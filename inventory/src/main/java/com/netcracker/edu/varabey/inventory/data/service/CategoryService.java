package com.netcracker.edu.varabey.inventory.data.service;

import com.netcracker.edu.varabey.inventory.data.entity.Category;

public interface CategoryService {
    /**
     * Saves a given category. Use the returned instance for further operations as the save operation might have changed the
     * category instance completely.
     *
     * @param category must not be {@literal null}.
     * @return the saved category will never be {@literal null}.
     */
    Category createCategory(Category category);

    /**
     * Retrieves a category by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the category with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Category findCategory(Long id);

    /**
     * Retrieves a category by name.
     *
     * @param name must not be {@literal null}.
     * @return the category with the given name or null if none found
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Category findCategory(String name);

    /**
     * Retrieves a category by name if it exists, otherwise creates new (not persisted) one.
     *
     * @param name must not be {@literal null}.
     * @return the category with the given name
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Category getByName(String name);
}
