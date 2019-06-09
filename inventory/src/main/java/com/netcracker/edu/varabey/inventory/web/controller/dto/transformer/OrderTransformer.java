package com.netcracker.edu.varabey.inventory.web.controller.dto.transformer;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import com.netcracker.edu.varabey.inventory.data.entity.Order;
import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.data.entity.utils.OrderStatus;
import com.netcracker.edu.varabey.inventory.web.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.inventory.web.controller.dto.OrderDTO;
import com.netcracker.edu.varabey.inventory.web.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrderTransformer implements Transformer<Order, OrderDTO> {
    private final Transformer<Customer, CustomerDTO> customerTransformer;
    private final Transformer<OrderItem, OrderItemDTO> itemTransformer;

    public OrderTransformer(Transformer<Customer, CustomerDTO> customerTransformer, Transformer<OrderItem, OrderItemDTO> itemTransformer) {
        this.customerTransformer = customerTransformer;
        this.itemTransformer = itemTransformer;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(orderDTO.getEmail());
        Customer customer = customerTransformer.toEntity(customerDTO);

        Order order = new Order(customer, orderDTO.getCreatedOnDate());
        order.setPaid((orderDTO.isPaid() == null) ? false : orderDTO.isPaid());
        try {
            order.setStatus(OrderStatus.valueOf(orderDTO.getOrderStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("\'").append(orderDTO.getOrderStatus()).append("\'")
                    .append(" is invalid OrderStatus. Consider one of the following: ")
                    .append(Stream.of(OrderStatus.values())
                            .map(OrderStatus::toString)
                            .collect(Collectors.joining(", ")));
            throw new OrderException(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        orderDTO.getItems().stream()
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
        dto.setOrderStatus(order.getStatus().toString());
        dto.setCreatedOnDate(order.getCreatedOnDate());
        order.getItems().stream()
                .map(itemTransformer::toDto)
                .forEach(dto::addItem);
        dto.setItemCount(order.getItemCount());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
