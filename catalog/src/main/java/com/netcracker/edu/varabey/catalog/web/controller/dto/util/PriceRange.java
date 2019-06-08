package com.netcracker.edu.varabey.catalog.web.controller.dto.util;

import lombok.Data;

@Data
public class PriceRange {
    private Double minPrice;
    private Double maxPrice;

    public PriceRange(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}
