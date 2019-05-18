package com.netcracker.edu.varabey.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long id = 0L;
    private CustomerDTO customer = new CustomerDTO();
    private String orderStatus = "";
    private Boolean isPaid = false;
    private LocalDateTime createdOnDate = LocalDateTime.now();
    private Double totalPrice = 0.0;
    private Integer itemCount = 0;
    private Set<OrderItemDTO> items = new HashSet<>();

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
