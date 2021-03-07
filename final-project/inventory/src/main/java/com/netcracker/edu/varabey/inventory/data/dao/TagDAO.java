package com.netcracker.edu.varabey.inventory.data.dao;

import com.netcracker.edu.varabey.inventory.data.entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagDAO extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
}
