package com.netcracker.edu.varabey.catalog.data.dao;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
