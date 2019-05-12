package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;

public interface CustomerService {
    /**
     * Saves a given customer. Use the returned instance for further operations as the save operation might have changed the
     * customer instance completely.
     *
     * @param customer must not be {@literal null}.
     * @return the saved customer will never be {@literal null}.
     * @throws InvalidCustomerException if customer is null || is detached || has invalid properties
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

    Customer findByEmail(String email);
}
