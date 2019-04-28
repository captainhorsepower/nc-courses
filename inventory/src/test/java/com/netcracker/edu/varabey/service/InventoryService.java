package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.dao.CustomerDAO;
import com.netcracker.edu.varabey.dao.OrderDAO;
import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private OrderDAO orderDAO;


    public Category createCategory(Category category) {
        return categoryDAO.save(category);
    }

    public Tag createTag(Tag tag) {
        return tagDAO.save(tag);
    }

    public Customer createCustomer(Customer customer) {
        return customerDAO.save(customer);
    }

    public Customer findCustomer(Long id) {
        return customerDAO.find(id);
    }

    public Category findCategory(Long id) {
        return categoryDAO.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Tag findTag(Long id) {
        return tagDAO.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public Order createOrder(Order order) {
        return orderDAO.save(order);
    }

    public Order findOrder(Long id) {
        return orderDAO.find(id);
    }

    public void deleteOrder(Long id) {
        orderDAO.delete(id);
    }

    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    public Order updateOrder(Order order) {
        return orderDAO.update(order);
    }

    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag) {
        return orderDAO.findAllOrderItemsByCustomerAndTag(customer, tag);
    }

    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category) {
        return orderDAO.findAllOrderItemsByCustomerAndCategory(customer, category);
    }
}
