package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;

public interface TagService {

    /**
     * Saves a given tags. Use the returned instance for further operations as the save operation might have changed the
     * tags instance completely.
     *
     * @param tag must not be {@literal null}.
     * @return the saved tags will never be {@literal null}.
     * @throws InvalidCategoryException if tags is null || is detached || has invalid properties
     */
    Tag createTag(Tag tag);

    /**
     * Retrieves a tags by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the tags with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Tag findTag(Long id);

    /**
     * Retrieves a tags by it's name.
     *
     * @param name must not be {@literal null}.
     * @return the tags with the given name or null if none found
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Tag findByName(String name);

    /**
     * Retrieves a tags by name if it exists, otherwise creates new (not persisted) one.
     *
     * @param name must not be {@literal null}.
     * @return the tags with the given name
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Tag getByName(String name);
}
