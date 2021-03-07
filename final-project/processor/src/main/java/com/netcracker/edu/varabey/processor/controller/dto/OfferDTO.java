package com.netcracker.edu.varabey.processor.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class OfferDTO {
    private Long id = 0L;
    private String name;
    private Double price;
    private String category;
    private Set<String> tags = new HashSet<>();

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
