package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCategoryException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidOrderException;
import com.netcracker.edu.varabey.service.validation.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderService {

    /**
     * Saves a given order. Use the returned instance for further operations as the save operation might have changed the
     * order instance completely.
     *
     * @param order must not be {@literal null}.
     * @return the saved order will never be {@literal null}.
     * @throws InvalidCategoryException if order is null || is detached || has invalid properties
     */
    Order save(Order order);

    /**
     * Retrieves an order by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the order with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Order findById(Long id);

    /**
     * Retrieves all orders.
     *
     * @return a List containing all found orders
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    List<Order> findAll();

    Order updatePaymentAndStatus(Order order);

    Order addItems(Long id, List<OrderItem> items);

    Order removeItems(Long id, List<OrderItem> items);

    /**
     * Removes an order with given id.
     * @param id - must not be null
     * @throws IllegalArgumentException if id is null
     */
    void deleteOrder(Long id);

    /**
     * Retrieves all orderItems of given customer having given tags.
     * @param customer must have valid id
     * @param tag must have valid id
     * @return List of all found OrderItems
     */
    List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag);

    /**
     * Retrieves all orderItems of given customer with given category.
     * @param customer must have valid id
     * @param category must have valid id
     * @return List of all found OrderItems
     */
    List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category);

    /**
     * Refreshes tags and categories that are already persisted
     *
     * @param items list of raw OrderItems
     * @return list of OrderItems with tags and categories ready for persist/update
     */
    List<OrderItem> getValidOrderItems(List<OrderItem> items);
}