package com.netcracker.edu.varabey.catalog.dao;

import com.netcracker.edu.varabey.catalog.entity.Tag;

import java.util.Collection;

public interface TagDAO {
    Tag save(Tag tag);
    Collection<Tag> saveAll(Collection<Tag> tag);
    Tag findById(Long id);
    Tag findByName(String name);
    void delete(Long id);
}
