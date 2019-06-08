package com.netcracker.edu.varabey.catalog.dao;

import com.netcracker.edu.varabey.catalog.entity.Tag;

import java.util.Collection;

public interface TagDAO {
    /**
     * Creates a tags in the Catalog database.
     * Tag should have null id and tag.offers should be empty.
     * Otherwise, expect issues with data.
     *
     * @param tag with set unique name and nothing else
     * @return given tags with initialized id.
     */
    Tag save(Tag tag);

    /**
     * Creates all given tags in the Catalog database.
     *
     * @param tags with set names and nothing else.
     * @return given collection of tags with initialized ids
     */
    Collection<Tag> saveAll(Collection<Tag> tag);

    /**
     * Retrieves the tag from the Catalog database.
     * @param id unique tag id
     * @return found tag.
     */
    Tag findById(Long id);

    /**
     * Retrieves the tag from the Catalog database.
     * @param id unique tag name
     * @return found tag.
     */
    Tag findByName(String name);

    /**
     * Removes a tag from the Catalog database.
     * @param id of the tag to be removed.
     */
    void delete(Long id);
}
