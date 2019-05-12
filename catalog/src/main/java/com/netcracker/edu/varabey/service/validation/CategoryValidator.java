package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;
import com.netcracker.edu.varabey.spring.Validator;
import lombok.Getter;

@Getter
@Validator
public class CategoryValidator implements ServiceValidator<Category, Long> {
    private final NameValidator categoryNameValidator;


    public CategoryValidator(NameValidator categoryNameValidator) {
        this.categoryNameValidator = categoryNameValidator;
    }

    @Override
    public Long extractId(Category resource) {
        return resource.getId();
    }

    @Override
    public void checkNotNull(Category resource) {
        if (resource == null) {
            throw new InvalidCategoryException("Category must be NOT null, but is.");
        }
    }

    @Override
    public void checkProperties(Category category) {
        categoryNameValidator.check(category.getName());
    }
}