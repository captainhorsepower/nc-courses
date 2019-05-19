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
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(dto.getEmail());
        Customer customer = customerTransformer.toEntity(customerDTO);

        Order order = new Order(customer, dto.getCreatedOnDate());
        order.setPaid(dto.isPaid());
        order.setStatus(dto.getOrderStatus());
        dto.getItems().stream()
                .map(itemTransformer::toEntity)
                .forEach(order::addItem);
        return order;
    }

    @Override
    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setEmail(order.getCustomer().getEmail());
        dto.setPaid(order.isPaid());
        dto.setOrderStatus(order.getStatus());
        dto.setCreatedOnDate(order.getCreatedOnDate());
        order.getItems().stream()
                .map(itemTransformer::toDto)
                .forEach(dto::addItem);
        dto.setItemCount(order.getItemCount());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
