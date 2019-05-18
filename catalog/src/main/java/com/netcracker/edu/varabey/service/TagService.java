package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidTagException;
import com.netcracker.edu.varabey.service.validation.exceptions.TagNotFoundException;

public interface TagService {

    /**
     * Saves a given tags
     * @param tag - new entity
     * @return saved entity\
     * @throws InvalidTagException if tags is null || invalid || detached
     */
    Tag create(Tag tag);

    /**
     * Finds tags by id
     * @param id - must not be null
     * @return found tags or null
     */
    Tag findById(Long id);

    /**
     * Finds tags by name
     * @param name - must not be null
     * @return found tags or null
     */
    Tag findByName(String name);

    Tag find(String input);

    /**
     * Finds tags by name
     * @param name - must not be null nor empty
     * @return found tags or null
     * @throws IllegalArgumentException if name is null or empty
     */
    Tag getByName(String name);

    /**
     * Allows to update tags's name
     * @param tag with updated name
     * @return updated tags
     * @throws InvalidTagException if tags has invalid properties or is null || new
     * @throws TagNotFoundException if tags was never saved before
     */
    Tag updateName(Tag tag);

    /**
     * Removes a tags.
     *
     * @param id of the tags to be removed.
     * @throws javax.persistence.EntityNotFoundException if there is no tags
     *      with given id in the database.
     * @throws IllegalArgumentException if id is null
     */
    void deleteTag(Long id);
}
