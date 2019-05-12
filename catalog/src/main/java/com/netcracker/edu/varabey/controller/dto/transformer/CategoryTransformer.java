package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.CategoryDTO;
import com.netcracker.edu.varabey.controller.dto.OfferDTO;
import com.netcracker.edu.varabey.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer implements Transformer<Category, CategoryDTO> {

    /* offers are not set, because category should not create any offers
     * id isn't set because category must have unique name, so there
     * are some complications */
    @Override
    public Category toEntity(CategoryDTO dto) {
        return new Category(dto.getName());
    }

    @Override
    public CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());

        /* this offer dto is simplified (doesn't contain tags) */
        category.getOffers().stream()
                .map( offer -> new OfferDTO(offer.getId(), offer.getName(), offer.getPrice().getValue(), offer.getCategory().getName()))
                .forEach(dto::addOffer);
        return dto;
    }
}
