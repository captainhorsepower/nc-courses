package com.netcracker.edu.varabey.inventory.data.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import com.netcracker.edu.varabey.inventory.data.entity.Order;
import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;

import java.time.LocalDateTime;

public interface OrderValidator extends ServiceValidator<Order, Long> {
    void checkCustomer(Customer c);
    void checkCreationDate(LocalDateTime dateTime);
    void checkOrderItem(OrderItem orderItem);
    void checkAllProperties(Order order);
    void checkForPersist(Order order);
    void checkEligibilityForUpdate(Order order);
    Order checkFoundById(Order order, Long id);
}
