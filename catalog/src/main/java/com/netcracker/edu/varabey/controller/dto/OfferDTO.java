package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class OfferDTO {
    private Long id;
    private String name;
    private Double priceValue;
    private String categoryName;
    private Set<String> tagNames;

    public OfferDTO(Long id, String name, Double priceValue, String categoryName) {
        this.id = id;
        this.name = name;
        this.priceValue = priceValue;
        this.categoryName = categoryName;
        tagNames = new HashSet<>();
    }

    public void addTag(String tagName) {
        tagNames.add(tagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferDTO offerDTO = (OfferDTO) o;
        return Objects.equals(id, offerDTO.id) &&
                name.equals(offerDTO.name) &&
                priceValue.equals(offerDTO.priceValue) &&
                categoryName.equals(offerDTO.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priceValue, categoryName);
    }
}
