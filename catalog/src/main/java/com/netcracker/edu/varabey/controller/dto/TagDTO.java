package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class TagDTO {
    private Long id;
    private String name;
    private Set<OfferDTO> offers;

    public TagDTO(Long id, String name) {
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
        TagDTO tagDTO = (TagDTO) o;
        return name.equals(tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}