package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;

import java.util.Collection;

public interface CategoryDAO {
    Category save(Category category);
    Collection<Category> createAll(Collection<Category> categories);
    Category findById(Long id);
    Category update(Category category);
    void delete(Long id);
}
