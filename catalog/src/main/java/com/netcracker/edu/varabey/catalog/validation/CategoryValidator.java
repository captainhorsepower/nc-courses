package com.netcracker.edu.varabey.catalog.validation;

import com.netcracker.edu.varabey.catalog.entity.Category;

public interface CategoryValidator extends ServiceValidator<Category, Long> {
    void checkName(String name);
    void checkForPersist(Category category);
    Category checkFoundByName(Category category, String name);
}
