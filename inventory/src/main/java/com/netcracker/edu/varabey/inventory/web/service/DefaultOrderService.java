package com.netcracker.edu.varabey.inventory.web.service;

import com.netcracker.edu.varabey.inventory.data.dao.OrderDAO;
import com.netcracker.edu.varabey.inventory.data.entity.*;
import com.netcracker.edu.varabey.inventory.data.entity.utils.OrderStatus;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.web.validation.CategoryValidator;
import com.netcracker.edu.varabey.inventory.web.validation.CustomerValidator;
import com.netcracker.edu.varabey.inventory.web.validation.OrderValidator;
import com.netcracker.edu.varabey.inventory.web.validation.TagValidator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
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

    protected Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);

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
        logger.info("Verifying order for persist...");
        orderValidator.checkForPersist(order);
        order.setItems(getValidOrderItems(order.getItems()));
        order.setCustomer(customerService.createCustomer(order.getCustomer()));
        logger.info("Saving order to the database...");
        return orderDAO.save(order);
    }

    @Override
    public Order findById(Long id) {
        orderValidator.checkIdIsNotNull(id);
        logger.info("Looking for order by Id");
        return orderDAO.findById(id);
    }

    @Logged(messageBefore = "Retrieving all orders from the database...", messageAfter = "All orders retrieved.")
    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Logged(messageBefore = "Retrieving all orders by email from the database...", messageAfter = "All orders retrieved.")
    @Override
    public List<Order> findAllOrdersByEmail(String coupledEmail) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(coupledEmail), coupledEmail);
        return orderDAO.findAllByCustomer(customer);
    }

    @Logged(messageBefore = "Retrieving all orders from the database...", messageAfter = "All orders retrieved.")
    @Override
    public List<Order> findAllOrdersByPaymentStatus(Boolean isPaid) {
        return orderDAO.findAllByPaymentStatus(isPaid);
    }

    @Override
    public Order updatePaymentAndStatus(Long id, Order order) {
        Order existingOrder = orderValidator.checkFoundById(findById(id), id);

        if (order.getStatus() != null) {
            existingOrder.setStatus(order.getStatus());
        }

        orderValidator.checkEligibilityForUpdate(order);
        if (order.isPaid() != null) {
            existingOrder.setPaid(order.isPaid());
        }

        return existingOrder;
    }

    @Override
    public Order setNextOrderStatus(Long id) {
        Order order = orderValidator.checkFoundById(findById(id), id);

        if (order.getStatus().ordinal() < OrderStatus.DELIVERED.ordinal()) {
            OrderStatus[] statuses = OrderStatus.values();
            for (int i = 0; i < statuses.length; i++) {
                if (statuses[i].equals(order.getStatus())) {
                    order.setStatus(statuses[Math.max(i + 1, (i + 1) % statuses.length)]);
                    break;
                }
            }
        }

        return order;
    }

    @Override
    public Order addItems(Long id, List<OrderItem> items) {
        Order order = orderValidator.checkFoundById(findById(id), id);
        orderValidator.checkEligibilityForUpdate(order);

        items = getValidOrderItems(items);
        items.forEach(order::addItem);

        return order;
    }

    @Override
    public Order removeItems(Long id, Set<Long> itemIds) {
        Order order = orderValidator.checkFoundById(findById(id), id);
        orderValidator.checkEligibilityForUpdate(order);

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
    public List<OrderItem> findAllOrderItemsByEmailAndTags(String email, Collection<String> tagNames) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        List<Tag> tags = tagNames.stream()
                .map(tagName -> tagValidator.checkFoundByName(tagService.findByName(tagName), tagName))
                .collect(Collectors.toList());
        return orderDAO.findAllOrderItemsByCustomerAndTags(customer, tags);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByEmailAndCategory(String email, String categoryName) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        Category category = categoryValidator.checkFoundByName(categoryService.findCategory(categoryName), categoryName);
        return orderDAO.findAllOrderItemsByCustomerAndCategory(customer, category);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByEmail(String email) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        return orderDAO.findAllOrderItemsByCustomer(customer);
    }

    @Override
    public Double getTotalMoneySpendByEmail(String email) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        return orderDAO.getTotalMoneySpendByCustomer(customer);
    }

    @Override
    public Long getItemCountBoughtByEmail(String email) {
        Customer customer = customerValidator.checkFoundByEmail(customerService.findByEmail(email), email);
        return orderDAO.getItemCountBoughtByCustomer(customer);
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