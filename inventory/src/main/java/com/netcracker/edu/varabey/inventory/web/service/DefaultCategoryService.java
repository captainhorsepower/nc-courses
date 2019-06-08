package com.netcracker.edu.varabey.inventory.web.service;

import com.netcracker.edu.varabey.inventory.data.dao.CategoryDAO;
import com.netcracker.edu.varabey.inventory.data.entity.Category;
import com.netcracker.edu.varabey.inventory.web.validation.CategoryValidator;
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
    public Category createCategory(Category category) {
        Category existingCategory = findCategory(category.getName());
        if (existingCategory != null) {
            return existingCategory;
        }
        categoryValidator.checkForPersist(category);
        return categoryDAO.save(category);
    }

    @Override
    public Category findCategory(Long id) {
        categoryValidator.checkIdIsNotNull(id);
        return categoryDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Category findCategory(String name) {
        categoryValidator.checkName(name);
        return categoryDAO.findByName(name);
    }

    @Override
    public Category getByName(String name) {
        Category foundCategory = findCategory(name);
        return (foundCategory == null) ? new Category(name) : foundCategory;
    }
}
