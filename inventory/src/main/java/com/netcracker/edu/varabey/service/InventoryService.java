package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.service.validation.InvalidCategoryException;
import com.netcracker.edu.varabey.service.validation.InvalidOrderException;
import com.netcracker.edu.varabey.service.validation.OrderNotFoundException;

import java.util.List;

public interface InventoryService {

    /**
     * Saves a given category. Use the returned instance for further operations as the save operation might have changed the
     * category instance completely.
     *
     * @param category must not be {@literal null}.
     * @return the saved category will never be {@literal null}.
     * @throws InvalidCategoryException if category is null || is detached || has invalid properties
     */
    Category createCategory(Category category);

    /**
     * Retrieves a category by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the category with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Category findCategory(Long id);

    /**
     * Retrieves a category by name.
     *
     * @param name must not be {@literal null}.
     * @return the category with the given name or null if none found
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Category findCategory(String name);

    /**
     * Saves a given tag. Use the returned instance for further operations as the save operation might have changed the
     * tag instance completely.
     *
     * @param tag must not be {@literal null}.
     * @return the saved tag will never be {@literal null}.
     * @throws InvalidCategoryException if tag is null || is detached || has invalid properties
     */
    Tag createTag(Tag tag);

    /**
     * Retrieves a tag by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the tag with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Tag findTag(Long id);

    /**
     * Retrieves a tag by it's name.
     *
     * @param name must not be {@literal null}.
     * @return the tag with the given name or null if none found
     * @throws IllegalArgumentException if {@code name} is {@literal null}.
     */
    Tag findTag(String name);

    /**
     * Saves a given customer. Use the returned instance for further operations as the save operation might have changed the
     * customer instance completely.
     *
     * @param customer must not be {@literal null}.
     * @return the saved customer will never be {@literal null}.
     * @throws InvalidCategoryException if customer is null || is detached || has invalid properties
     */
    Customer createCustomer(Customer customer);

    /**
     * Retrieves a customer by his/her id.
     *
     * @param id must not be {@literal null}.
     * @return the customer with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Customer findCustomer(Long id);


    /**
     * Saves a given order. Use the returned instance for further operations as the save operation might have changed the
     * order instance completely.
     *
     * @param order must not be {@literal null}.
     * @return the saved order will never be {@literal null}.
     * @throws InvalidCategoryException if order is null || is detached || has invalid properties
     */
    Order createOrder(Order order);

    /**
     * Retrieves an order by it's id.
     *
     * @param id must not be {@literal null}.
     * @return the order with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Order findOrder(Long id);

    /**
     * Retrieves all orders.
     *
     * @return a List containing all found orders
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    List<Order> findAllOrders();

    /**
     * Updates an order.
     * @param order with updates
     * @return updated order
     * @throws InvalidOrderException if order is null or has invalid properties
     * @throws OrderNotFoundException if order wasn't saved before.
     */
    Order updateOrder(Order order);

    /**
     * Removes an order with given id.
     * @param id - must not be null
     * @throws IllegalArgumentException if id is null
     */
    void deleteOrder(Long id);

    /**
     * Retrieves all orderItems of given customer having given tag.
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
}