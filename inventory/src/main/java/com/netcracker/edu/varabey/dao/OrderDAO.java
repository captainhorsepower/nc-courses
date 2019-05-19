package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.*;

import java.util.List;

public interface OrderDAO {
    Order save(Order order);
    Order find(Long id);
    List<Order> findAll();
    Order update(Order order);
    void delete(Long id);

    List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer c, Tag t);
    List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca);
    List<OrderItem> findAllOrderItemsByCustomer(Customer customer);
}
