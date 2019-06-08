package com.netcracker.edu.varabey.inventory.data.validation;

import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.data.entity.Price;

public interface OrderItemValidator extends ServiceValidator<OrderItem, Long> {
    void checkName(String name);
    void checkPrice(Price price);
    void checkForPersist(OrderItem offer);
}
