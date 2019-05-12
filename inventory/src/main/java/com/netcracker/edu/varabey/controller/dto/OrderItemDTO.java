package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private String name;
    private Double priceValue;
    private String category;
    private Set<String> tags;

    public OrderItemDTO(Long id, Long orderId, String name, Double priceValue, String category) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.priceValue = priceValue;
        this.category = category;
        tags = new HashSet<>();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }
}
