package com.netcracker.edu.varabey.inventory.web.controller.dto.transformer;

import com.netcracker.edu.varabey.inventory.data.entity.Category;
import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.data.entity.Tag;
import com.netcracker.edu.varabey.inventory.web.controller.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemTransformer implements Transformer<OrderItem, OrderItemDTO> {
    @Override
    public OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem(dto.getPrice(), dto.getName().trim(), new Category(dto.getCategory()));
        item.setId(dto.getId());
        dto.getTags().stream()
                .map(Tag::new)
                .forEach(item::addTag);
        return item;
    }

    @Override
    public OrderItemDTO toDto(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO(item.getId(), item.getOwningOrder().getId(), item.getName(), item.getPrice().getValue(), item.getCategory().getName());
        item.getTags().stream()
                .map(Tag::getName)
                .forEach(dto::addTag);
        return dto;
    }
}
