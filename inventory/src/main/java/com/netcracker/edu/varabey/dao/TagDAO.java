package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagDAO extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
}
