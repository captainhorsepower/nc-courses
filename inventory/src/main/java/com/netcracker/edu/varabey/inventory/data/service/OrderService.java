package com.netcracker.edu.varabey.inventory.data.service;

import com.netcracker.edu.varabey.inventory.data.entity.*;

import java.util.List;
import java.util.Set;

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

    Order updatePaymentAndStatus(Long id, Order order);

    Order addItems(Long id, List<OrderItem> items);

    Order removeItems(Long id, Set<Long> itemIds);

    /**
     * Removes an order with given id.
     * @param id - must not be null
     * @throws IllegalArgumentException if id is null
     */
    void deleteOrder(Long id);

    /**
     * Retrieves all orderItems of given email having given tags.
     * @param customer must have valid id
     * @param tag must have valid id
     * @return List of all found OrderItems
     */
    List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer customer, Tag tag);

    /**
     * Retrieves all orderItems of given email with given category.
     * @param customer must have valid id
     * @param category must have valid id
     * @return List of all found OrderItems
     */
    List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer customer, Category category);

    List<OrderItem> findAllOrderItemsByCustomer(Customer customer);

    /**
     * Refreshes tags and categories that are already persisted
     *
     * @param items list of raw OrderItems
     * @return list of OrderItems with tags and categories ready for persist/update
     */
    List<OrderItem> getValidOrderItems(List<OrderItem> items);

    List<Order> findAllOrdersByEmail(String coupledEmail);

    List<Order> findAllOrdersByPaymentStatus(Boolean isPaid);
}