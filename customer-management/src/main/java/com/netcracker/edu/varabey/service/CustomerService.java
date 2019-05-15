package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;

import java.util.List;

public interface CustomerService {
    /**
     * Saves a given customer.
     * @param c new customer with valid fields
     * @return saved customer
     * @throws InvalidCustomerException if customer is null || managed || detached
     */
    Customer save(Customer c);

    /**
     * Retrieves a customer by his(her) id
     * @param id - must not be null
     * @return found Customer or null customer wasn't found
     */
    Customer find(Long id);

    /**
     * Retrieves a customer by his(her) email
     * @param email - must not be null
     * @return found Customer or null customer wasn't found
     */
    Customer findByEmail(String email);

    /**
     * @return list of all Customers (or empty list)
     */
    List<Customer> findAll();

    /**
     * Updates customer with given id (must not be null)
     * @param c customer with updates
     * @return updated customer
     * @throws InvalidCustomerException if any customer field is null or if customer is a new entity
     */
    Customer update(Customer c);

    /**
     * @param id - id of the target customer
     */
    void delete(Long id);
}
