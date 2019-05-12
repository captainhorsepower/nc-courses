package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;

public interface TagService {

    /**
     * Saves a given tag. Use the returned instance for further operations as the save operation might have changed the
     * tag instance completely.
     *
     * @param tag must not be {@literal null}.
     * @return the saved tag will never be {@literal null}.
     * @throws InvalidCategoryException if tag is null || is detached || has invalid properties
     */
    Tag createTag(Tag tag);

    /**
     * Retrieves a tag by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the tag with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Tag findTag(Long id);

    /**
     * Retrieves a tag by it's name.
     *
     * @param name must not be {@literal null}.
     * @return the tag with the given name or null if none found
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Tag findByName(String name);

    /**
     * Retrieves a tag by name if it exists, otherwise creates new (not persisted) one.
     *
     * @param name must not be {@literal null}.
     * @return the tag with the given name
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Tag getByName(String name);
}
