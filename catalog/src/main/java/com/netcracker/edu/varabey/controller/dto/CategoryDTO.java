package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Set<OfferDTO> offers;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        offers = new HashSet<>();
    }

    public void addOffer(OfferDTO offerDTO) {
        offers.add(offerDTO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
