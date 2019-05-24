package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Category;

public interface CategoryValidator extends ServiceValidator<Category, Long> {
    void checkName(String name);
    void checkForPersist(Category category);
    Category checkFoundByName(Category category, String name);
}
