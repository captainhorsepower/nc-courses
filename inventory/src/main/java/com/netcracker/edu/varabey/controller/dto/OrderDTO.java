package com.netcracker.edu.varabey.controller.dto;

import com.netcracker.edu.varabey.entity.utils.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private CustomerDTO customer;
    private LocalDateTime createdOnDate;
    private OrderStatus orderStatus;
    private Boolean isPaid;
    private Double totalPrice;
    private Integer itemCount;
    private List<OrderItemDTO> items;

    public OrderDTO(Long id, CustomerDTO customer, LocalDateTime createdOnDate, OrderStatus orderStatus, Boolean isPaid, Double totalPrice, Integer itemCount) {
        this.id = id;
        this.customer = customer;
        this.createdOnDate = createdOnDate;
        this.orderStatus = orderStatus;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.itemCount = itemCount;
        items = new ArrayList<>();
    }

    public void addItem(OrderItemDTO item) {
        items.add(item);
    }

    public void setPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean isPaid() {
        return this.isPaid;
    }
}
