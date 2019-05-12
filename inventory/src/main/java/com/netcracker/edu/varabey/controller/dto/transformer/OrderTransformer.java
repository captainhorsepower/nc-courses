package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.OrderDTO;
import com.netcracker.edu.varabey.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.entity.Order;
import com.netcracker.edu.varabey.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderTransformer implements Transformer<Order, OrderDTO> {
    private final Transformer<Customer, CustomerDTO> customerTransformer;
    private final Transformer<OrderItem, OrderItemDTO> itemTransformer;

    public OrderTransformer(Transformer<Customer, CustomerDTO> customerTransformer, Transformer<OrderItem, OrderItemDTO> itemTransformer) {
        this.customerTransformer = customerTransformer;
        this.itemTransformer = itemTransformer;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        Order order = new Order(customerTransformer.toEntity(dto.getCustomer()), dto.getCreatedOnDate());
        order.setPaid(dto.isPaid());
        order.setStatus(dto.getOrderStatus());
        dto.getItems().stream()
                .map(itemTransformer::toEntity)
                .forEach(order::addItem);
        return order;
    }

    @Override
    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO(order.getId(), customerTransformer.toDto(order.getCustomer()), order.getCreatedOnDate(), order.getStatus(), order.getIsPaid(), order.getTotalPrice(), order.getItemCount());
        order.getItems().stream()
                .map(itemTransformer::toDto)
                .forEach(dto::addItem);
        return dto;
    }
}
