package com.netcracker.edu.varabey.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderItemDTO {
    private Long id = 0L;
    private Long orderId = 0L;
    private String name = "";
    private Double price = 0.0;
    private String category = "";
    private Set<String> tags = new HashSet<>();
}
