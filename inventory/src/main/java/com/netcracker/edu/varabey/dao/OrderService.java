package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.*;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Order read(Long id);
    List<Order> readAll();
    Order update(Order order);
    void delete(Long id);

    List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer c, Tag t);
    List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca);
}
