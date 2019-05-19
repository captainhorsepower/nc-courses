package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private String name;
    private Double price;
    private String category;
    private Set<String> tags = new HashSet<>();

    public OrderItemDTO(Long id, Long orderId, String name, Double price, String category) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }
}
