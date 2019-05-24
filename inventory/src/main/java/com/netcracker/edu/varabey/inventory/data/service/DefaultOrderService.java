package com.netcracker.edu.varabey.inventory.data.service;

import com.netcracker.edu.varabey.inventory.data.dao.OrderDAO;
import com.netcracker.edu.varabey.inventory.data.entity.*;
import com.netcracker.edu.varabey.inventory.exceptions.OrderException;
import com.netcracker.edu.varabey.inventory.validation.CategoryValidator;
import com.netcracker.edu.varabey.inventory.validation.CustomerValidator;
import com.netcracker.edu.varabey.inventory.validation.OrderValidator;
import com.netcracker.edu.varabey.inventory.validation.TagValidator;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultOrderService implements OrderService {
    private final OrderDAO orderDAO;
    private final CustomerService customerService;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final OrderValidator orderValidator;
    private final CustomerValidator customerValidator;
    private final TagValidator tagValidator;
    private final CategoryValidator categoryValidator;

    public DefaultOrderService(OrderDAO orderDAO, CustomerService customerService, TagService tagService, CategoryService categoryService, OrderValidator orderValidator, CustomerValidator customerValidator, TagValidator tagValidator, CategoryValidator categoryValidator) {
        this.orderDAO = orderDAO;
        this.customerService = customerService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.orderValidator = orderValidator;
        this.customerValidator = customerValidator;
        this.tagValidator = tagValidator;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public Order save(Order order) {
        orderValidator.checkForPersist(order);
        order.setItems(getValidOrderItems(order.getItems()));
        order.setCustomer(customerService.createCustomer(order.getCustomer()));
        return orderDAO.save(order);
    }

    @Override
    public Order findById(Long id) {
        orderValidator.checkIdIsNotNull(id);
        return orderDAO.find(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order updatePaymentAndStatus(Long id, Order order) {
        Order existingOrder = orderValidator.checkFoundById(findById(id), id);

        if (order.getStatus() != null) {
            existingOrder.setStatus(order.getStatus());
        }
        if (order.isPaid() != null) {
            existingOrder.setPaid(order.isPaid());
        }

        return existingOrder;
    }

    @Override
    public Order addItems(Long id, List<OrderItem> items) {
        Order order = orderValidator.checkFoundById(findById(id), id);

        items = getValidOrderItems(items);
        items.forEach(order::addItem);

        return order;
    }

    @Override
    public Order removeItems(Long id, Set<Long> itemIds) {
        Order order = orderValidator.checkFoundById(findById(id), id);

        List<OrderItem> trashCan = order.getItems().stream()
                .filter(i -> itemIds.contains(i.getId()))
                .collect(Collectors.toList());
        trashCan.forEach(order::removeItem);

        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderValidator.checkIdIsNotNull(id);
        try {
            orderDAO.delete(id);
        } catch (EntityNotFoundException | JpaObjectRetrievalFailureException e) {
            throw new OrderException("Order with id=" + id + " was not found. Unable to delete.");
        }
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag) {
        customer = customerValidator.checkFoundByEmail(customerService.findByEmail(customer.getEmail()), customer.getEmail());
        tag = tagValidator.checkFoundByName(tagService.findByName(tag.getName()), tag.getName());
        return orderDAO.findAllOrderItemsByCustomerAndTag(customer, tag);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category) {
        customer = customerValidator.checkFoundByEmail(customerService.findByEmail(customer.getEmail()), customer.getEmail());
        category = categoryValidator.checkFoundByName(categoryService.findCategory(category.getName()), category.getName());

        return orderDAO.findAllOrderItemsByCustomerAndCategory(customer, category);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomer(Customer customer) {
        customer = customerService.findByEmail(customer.getEmail());
        customerValidator.checkFoundByEmail(customer, customer.getEmail());
        return orderDAO.findAllOrderItemsByCustomer(customer);
    }

    @Override
    public List<OrderItem> getValidOrderItems(List<OrderItem> items) {
        return items.stream()
                .peek(item -> {
                    item.setCategory(categoryService.createCategory(item.getCategory()));
                    item.setTags(item.getTags().stream().map(tagService::createTag).collect(Collectors.toSet()));
                })
                .collect(Collectors.toList());
    }
}