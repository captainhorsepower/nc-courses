package com.netcracker.edu.varabey.catalog.data.validation;

import com.netcracker.edu.varabey.catalog.data.entity.Category;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.CategoryException;
import com.netcracker.edu.varabey.catalog.data.validation.util.NameValidator;
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

    @Logged(messageBefore = "Verifying category's name...", messageAfter = "done.")
    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Logged(messageBefore = "Verifying category is found...", messageAfter = "done.")
    @Override
    public Category checkFound(Category category, String notFoundMessage) {
        if (category == null) {
            throw new CategoryException(notFoundMessage);
        }
        return category;
    }

    @Logged(messageBefore = "Making sure category is found by name...", messageAfter = "done.")
    @Override
    public Category checkFoundByName(Category category, String name) {
        return checkFound(category, "Category with name=\'" + name + "\' was not found");
    }

    @Logged(messageBefore = "Making sure category is found by id...", messageAfter = "done.")
    @Override
    public Category checkFoundById(Category category, Long id) {
        return checkFound(category, "Category with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Verifying category is correct...", messageAfter = "done.")
    @Override
    public void checkAllProperties(Category category) {
        nameValidator.check(category.getName());
    }

    @Logged(messageBefore = "Verifying category is fine for persist...", messageAfter = "done.")
    @Override
    public void checkForPersist(Category category) {
        checkNotNull(category);
        checkIdIsNull(category.getId());
        checkAllProperties(category);
    }
}