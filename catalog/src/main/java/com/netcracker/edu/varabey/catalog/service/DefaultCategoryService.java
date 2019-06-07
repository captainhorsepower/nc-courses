package com.netcracker.edu.varabey.catalog.service;

import com.netcracker.edu.varabey.catalog.dao.CategoryDAO;
import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.validation.CategoryValidator;
import com.netcracker.edu.varabey.catalog.validation.exceptions.CategoryException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCategoryService implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final CategoryValidator categoryValidator;

    public DefaultCategoryService(CategoryDAO categoryDAO, CategoryValidator categoryValidator) {
        this.categoryDAO = categoryDAO;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public Category save(Category category) {
        categoryValidator.checkForPersist(category);
        Category existingCategory = findByName(category.getName());
        if (existingCategory != null) {
            return existingCategory;
        }
        return categoryDAO.save(category);
    }

    @Override
    public Category findById(Long id) {
        categoryValidator.checkIdIsNotNull(id);
        return categoryDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Category findByName(String name) {
        categoryValidator.checkName(name);
        return categoryDAO.findByName(name);
    }

    @Override
    public Category find(String input) {
        if (input.matches("\\d+")) {
            Long id = Long.parseLong(input);
            return findById(id);
        } else {
            String name = input.replaceAll("%20", " ");
            return findByName(name);
        }
    }

    @Override
    public Category getByName(String name) {
        Category foundCategory = findByName(name);
        return (foundCategory == null) ? new Category(name) : foundCategory;
    }

    @Override
    public Category updateName(Category category) {
        categoryValidator.checkAllProperties(category);
        categoryValidator.checkIdIsNotNull(category.getId());

        Category sourceCategory = categoryValidator.checkFoundById(categoryDAO.findById(category.getId()).orElse(null), category.getId());

        sourceCategory.setName(category.getName());

        return sourceCategory;
    }

    @Override
    public void delete(Long id) {
        categoryValidator.checkIdIsNotNull(id);
        try {
            categoryDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CategoryException("Category with id=" + id + " was not found. Unable to delete.");
        }
    }
}
