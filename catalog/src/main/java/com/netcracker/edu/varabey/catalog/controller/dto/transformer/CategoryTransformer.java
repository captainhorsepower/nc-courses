package com.netcracker.edu.varabey.catalog.controller.dto.transformer;

import com.netcracker.edu.varabey.catalog.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.catalog.entity.Category;
import com.netcracker.edu.varabey.catalog.entity.Offer;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer implements Transformer<Category, CategoryDTO> {

    /* offers are not set, because category should not create any offers
     * id isn't set because category must have unique name, so there
     * are some complications */
    @Override
    public Category toEntity(CategoryDTO dto) {
        String name = (dto.getName() == null) ? null : dto.getName().trim();
        return new Category(name);
    }

    @Override
    public CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());

        category.getOffers().stream()
                .mapToLong(Offer::getId)
                .forEach(dto::addOffer);
        return dto;
    }
}
