package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
