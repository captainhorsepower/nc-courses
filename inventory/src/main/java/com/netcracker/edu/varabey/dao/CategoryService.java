package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Category;

import java.util.Collection;

public interface CategoryService {
    Category create(Category category);
    Collection<Category> createAll(Collection<Category> categories);
    Category read(Long id);
    Category update(Category category);
    void delete(Long id);
}
