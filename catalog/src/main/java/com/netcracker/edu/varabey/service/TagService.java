package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidTagException;
import com.netcracker.edu.varabey.service.validation.exceptions.TagNotFoundException;

public interface TagService {

    /**
     * Saves a given tag
     * @param tag - new entity
     * @return saved entity\
     * @throws InvalidTagException if tag is null || invalid || detached
     */
    Tag create(Tag tag);

    /**
     * Finds tag by id
     * @param id - must not be null
     * @return found tag or null
     */
    Tag findById(Long id);

    /**
     * Finds tag by name
     * @param name - must not be null
     * @return found tag or null
     */
    Tag findTagByName(String name);

    /**
     * Finds tag by name
     * @param name - must not be null nor empty
     * @return found tag or null
     * @throws IllegalArgumentException if name is null or empty
     */
    Tag getByName(String name);

    /**
     * Allows to update tag's name
     * @param tag with updated name
     * @return updated tag
     * @throws InvalidTagException if tag has invalid properties or is null || new
     * @throws TagNotFoundException if tag was never saved before
     */
    Tag updateName(Tag tag);

    /**
     * Removes a tag.
     *
     * @param id of the tag to be removed.
     * @throws javax.persistence.EntityNotFoundException if there is no tag
     *      with given id in the database.
     * @throws IllegalArgumentException if id is null
     */
    void deleteTag(Long id);
}
