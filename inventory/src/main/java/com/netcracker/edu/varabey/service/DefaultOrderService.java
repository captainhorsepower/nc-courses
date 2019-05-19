package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.OrderDAO;
import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ServiceValidator<Order, Long> orderValidator;
    private final ServiceValidator<Customer, Long> customerValidator;
    private final ServiceValidator<Tag, Long> tagValidator;
    private final ServiceValidator<Category, Long> categoryValidator;
    private final ServiceValidator<OrderItem, Long> orderItemValidator;

    public DefaultOrderService(OrderDAO orderDAO, CustomerService customerService, TagService tagService, CategoryService categoryService, ServiceValidator<Order, Long> orderValidator, ServiceValidator<Customer, Long> customerValidator, ServiceValidator<Tag, Long> tagValidator, ServiceValidator<Category, Long> categoryValidator, ServiceValidator<OrderItem, Long> orderItemValidator) {
        this.orderDAO = orderDAO;
        this.customerService = customerService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.orderValidator = orderValidator;
        this.customerValidator = customerValidator;
        this.tagValidator = tagValidator;
        this.categoryValidator = categoryValidator;
        this.orderItemValidator = orderItemValidator;
    }

    @Override
    public Order save(Order order) {
        orderValidator.checkForPersist(order);
        order.setItems( getValidOrderItems(order.getItems()) );
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
        Order existingOrder = findById(id);
        orderValidator.checkFoundById(existingOrder, id);

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
        Order order = findById(id);
        orderValidator.checkFoundById(order, id);

        items = getValidOrderItems(items);
        items.forEach(order::addItem);

        return order;
    }

    @Override
    public Order removeItems(Long id, Set<Long> itemIds) {
        Order order = findById(id);
        orderValidator.checkFoundById(order, id);

        List<OrderItem> trashCan = order.getItems().stream()
                .filter(i -> itemIds.contains(i.getId()))
                .collect(Collectors.toList());

        trashCan.forEach(order::removeItem);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderValidator.checkIdIsNotNull(id);
        orderDAO.delete(id);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag) {
        customer = customerService.findByEmail(customer.getEmail());
        customerValidator.checkFound(customer, "Customer with email\"" + customer.getEmail() + "\" was not found.");

        tag = tagService.findByName(tag.getName());
        tagValidator.checkFound(tag, "Tag with name=\"" + tag.getName() + "\" was not found.");

        return orderDAO.findAllOrderItemsByCustomerAndTag(customer, tag);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category) {
        customer = customerService.findByEmail(customer.getEmail());
        customerValidator.checkFound(customer, "Customer with email\"" + customer.getEmail() + "\" was not found.");

        category = categoryService.findCategory(category.getName());
        categoryValidator.checkFound(category, "Category with name=\"" + category.getName() + "\" was not found.");

        return orderDAO.findAllOrderItemsByCustomerAndCategory(customer, category);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomer(Customer customer) {
        customer = customerService.findByEmail(customer.getEmail());
        customerValidator.checkFound(customer, "Customer with email\"" + customer.getEmail() + "\" was not found.");
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