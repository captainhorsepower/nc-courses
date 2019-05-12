package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import com.netcracker.edu.varabey.service.validation.exceptions.CategoryNotFoundException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;
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
        categoryNameValidator.check(name);
        return categoryDAO.findByName(name);
    }

    @Override
    public Category getByName(String name) {
        Category foundCategory = findByName(name);
        return (foundCategory == null) ? new Category(name) : foundCategory;
    }

    @Override
    public Category updateName(Category category) throws InvalidCategoryException {
        categoryValidator.checkForUpdate(category);

        Category sourceCategory = categoryDAO.findById(category.getId()).orElse(null);
        categoryValidator.checkFoundById(sourceCategory, category.getId());

        sourceCategory.setName(category.getName());
        return sourceCategory;
    }

    @Override
    public void delete(Long id) {
        categoryValidator.checkIdIsNotNull(id);
        categoryDAO.deleteById(id);
    }
}
