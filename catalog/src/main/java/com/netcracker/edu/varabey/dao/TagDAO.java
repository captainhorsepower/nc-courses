package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;

import java.util.Collection;

public interface TagDAO {
    Tag create(Tag tag);
    Collection<Tag> createAll(Collection<Tag> tag);
    Tag read(Long id);
    Tag update(Tag tag);
    void delete(Long id);
}
