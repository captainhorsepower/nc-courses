package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CategoryDAO;
import com.netcracker.edu.varabey.dao.CustomerDAO;
import com.netcracker.edu.varabey.dao.OrderDAO;
import com.netcracker.edu.varabey.dao.TagDAO;
import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.service.validation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DefaultInventoryService implements InventoryService {

    private final CategoryDAO categoryDAO;

    private final TagDAO tagDAO;

    private final CustomerDAO customerDAO;

    private final OrderDAO orderDAO;

    public static final int MINIMUM_NAME_LENGTH = 3;

    public DefaultInventoryService(CategoryDAO categoryDAO, TagDAO tagDAO, CustomerDAO customerDAO, OrderDAO orderDAO) {
        this.categoryDAO = categoryDAO;
        this.tagDAO = tagDAO;
        this.customerDAO = customerDAO;
        this.orderDAO = orderDAO;
    }



    @Override
    public Category createCategory(Category category) {
        if (category == null || category.getId() != null
                || category.getName() == null
                || category.getName().length() < MINIMUM_NAME_LENGTH) {
            throw new InvalidCategoryException();
        }
        return categoryDAO.save(category);
    }

    @Override
    public Category findCategory(Long id) {
        return categoryDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Category findCategory(String name) {
        if (name == null || name.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("invalid name");
        }
        return categoryDAO.findByName(name);
    }

    @Override
    public Tag createTag(Tag tag) {
        if (tag == null || tag.getId() != null
                || tag.getName() == null || tag.getName().length() < MINIMUM_NAME_LENGTH){
            throw new InvalidTagException();
        }
        return tagDAO.save(tag);
    }

    @Override
    public Tag findTag(Long id) {
        return tagDAO.findById(id).orElse(null);
    }

    @Override
    public Tag findTag(String name) {
        if (name == null || name.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("invalid name");
        }
        return tagDAO.findByName(name);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customer == null || customer.getId() != null
                || customer.getAge() == null || customer.getAge() < 1
                || customer.getFio() == null || customer.getFio().length() < MINIMUM_NAME_LENGTH) {
            throw new InvalidCustomerException();
        }
        return customerDAO.save(customer);
    }

    @Override
    public Customer findCustomer(Long id) {
        return customerDAO.findById(id).orElse(null);
    }




    @Override
    public Order createOrder(Order order) {
        if (order == null || order.getId() != null
                || order.getCustomer() == null
                || order.getIsPaid() == null
                || order.getCreatedOnDate() == null) {
            throw new InvalidOrderException();
        }
        return orderDAO.save(order);
    }

    @Override
    public Order findOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null (findOrder())");
        }
        return orderDAO.find(id);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public Order updateOrder(Order order) {
        if (order == null || order.getId() == null
                || order.getCustomer() == null
                || order.getCreatedOnDate() == null) {
            throw new InvalidOrderException();
        }

        Order sourceOrder = orderDAO.find(order.getId());
        if (sourceOrder == null) {
            throw new OrderNotFoundException();
        }

        return orderDAO.update(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must on be null (deleteOrder())");
        }
        orderDAO.delete(id);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag) {
        if (customer == null || customer.getId() == null
                || !customerDAO.existsById(customer.getId())) {
            throw new InvalidCustomerException();
        }
        
        if (tag == null || tag.getId() == null 
                || !tagDAO.existsById(tag.getId())) {
            throw new InvalidTagException();
        }
        
        return orderDAO.findAllOrderItemsByCustomerAndTag(customer, tag);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category) {
        if (customer == null || customer.getId() == null
                || !customerDAO.existsById(customer.getId())) {
            throw new InvalidCustomerException();
        }

        if (category == null || category.getId() == null
                || !categoryDAO.existsById(category.getId())) {
            throw new InvalidTagException();
        }

        return orderDAO.findAllOrderItemsByCustomerAndCategory(customer, category);
    }
}