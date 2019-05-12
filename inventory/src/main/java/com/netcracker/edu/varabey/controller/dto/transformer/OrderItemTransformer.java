package com.netcracker.edu.varabey.controller.dto.transformer;

import com.netcracker.edu.varabey.controller.dto.OrderItemDTO;
import com.netcracker.edu.varabey.entity.Category;
import com.netcracker.edu.varabey.entity.OrderItem;
import com.netcracker.edu.varabey.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class OrderItemTransformer implements Transformer<OrderItem, OrderItemDTO> {
    @Override
    public OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem(dto.getPriceValue(), dto.getName().trim(), new Category(dto.getCategory()));
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
