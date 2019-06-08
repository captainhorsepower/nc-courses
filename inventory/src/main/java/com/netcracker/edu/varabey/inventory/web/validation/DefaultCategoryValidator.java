package com.netcracker.edu.varabey.inventory.web.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Category;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.CategoryException;
import com.netcracker.edu.varabey.inventory.web.validation.fragments.NameValidator;
import lombok.Getter;

@Getter
@Validator
public class DefaultCategoryValidator implements CategoryValidator {
    private final NameValidator nameValidator;

    public DefaultCategoryValidator(NameValidator categoryNameValidator) {
        this.nameValidator = categoryNameValidator;
    }

    @Override
    public void checkNotNull(Category resource) {
        if (resource == null) {
            throw new CategoryException("Category must be NOT null, but is.");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new CategoryException("Category's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new CategoryException("Category's id must be NOT null");
        }
    }

    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Logged(messageBefore = "Verifying category is found...")
    @Override
    public Category checkFound(Category category, String notFoundMessage) {
        if (category == null) {
            throw new CategoryException(notFoundMessage);
        }
        return category;
    }

    @Logged(messageBefore = "Verifying category is found by name...")
    @Override
    public Category checkFoundByName(Category category, String name) {
        return checkFound(category, "Category with name=\'" + name + "\' was not found");
    }

    @Logged(messageBefore = "Verifying category is found by id...")
    @Override
    public Category checkFoundById(Category category, Long id) {
        return checkFound(category, "Category with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Verifying category is correct...")
    @Override
    public void checkAllProperties(Category category) {
        nameValidator.check(category.getName());
    }

    @Logged(messageBefore = "Verifying category is fine for persist...")
    @Override
    public void checkForPersist(Category category) {
        checkNotNull(category);
        checkIdIsNull(category.getId());
        checkAllProperties(category);
    }
}