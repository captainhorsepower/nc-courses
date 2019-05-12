package com.netcracker.edu.varabey.controller.dto.util;

import lombok.Data;

import java.util.Set;

@Data
public class OfferFilterDTO {
    private Set<String> tags;
    private String category;
    private PriceRange priceRange;
}
