package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Tag;
import com.netcracker.edu.varabey.service.validation.InvalidTagException;
import com.netcracker.edu.varabey.service.validation.TagNotFoundException;

interface TagService {

    /**
     * Saves a given tag
     * @param tag - new entity
     * @return saved entity\
     * @throws InvalidTagException if tag is null || invalid || detached
     */
    Tag createTag(Tag tag);

    /**
     * Finds tag by id
     * @param id - must not be null
     * @return found tag or null
     * @throws IllegalArgumentException if id is null
     */
    Tag findTag(Long id);

    /**
     * Allows to update tag's name
     * @param tag with updated name
     * @return updated tag
     * @throws InvalidTagException if tag has invalid properties or is null || new
     * @throws TagNotFoundException if tag was never saved before
     */
    Tag updateTag(Tag tag);

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
