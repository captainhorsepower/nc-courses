package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCategoryService implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final NameValidator categoryNameValidator;

    public DefaultCategoryService(CategoryDAO categoryDAO, ServiceValidator<Category, Long> categoryValidator, NameValidator categoryNameValidator) {
        this.categoryDAO = categoryDAO;
        this.categoryValidator = categoryValidator;
        this.categoryNameValidator = categoryNameValidator;
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
        categoryNameValidator.check(name);
        return categoryDAO.findByName(name);
    }

    @Override
    public Category getByName(String name) {
        Category foundCategory = findCategory(name);
        return (foundCategory == null) ? new Category(name) : foundCategory;
    }
}
