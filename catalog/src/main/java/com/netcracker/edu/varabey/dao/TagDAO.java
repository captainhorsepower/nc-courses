package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;

import java.util.Collection;

public interface TagDAO {
    Tag save(Tag tag);
    Collection<Tag> saveAll(Collection<Tag> tag);
    Tag find(Long id);
    Tag update(Tag tag);
    void delete(Long id);
}
