package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;

import java.util.Collection;

public interface TagDAO {
    Tag save(Tag tag);
    Collection<Tag> saveAll(Collection<Tag> tag);
    Tag findById(Long id);
    Tag findByName(String name);
    void delete(Long id);
}
