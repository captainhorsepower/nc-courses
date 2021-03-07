package com.netcracker.edu.varabey.catalog.web.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class OfferDTO {
    private Long id;
    private String name;
    private Double price;
    private String category;
    private Set<String> tags;

    public OfferDTO(Long id, String name, Double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        tags = new HashSet<>();
    }

    public void addTag(String tagName) {
        tags.add(tagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferDTO offerDTO = (OfferDTO) o;
        return Objects.equals(id, offerDTO.id) &&
                name.equals(offerDTO.name) &&
                price.equals(offerDTO.price) &&
                category.equals(offerDTO.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category);
    }
}
