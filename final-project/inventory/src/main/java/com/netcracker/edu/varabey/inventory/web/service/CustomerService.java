package com.netcracker.edu.varabey.inventory.web.service;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;

public interface CustomerService {
    /**
     * Saves a given email. Use the returned instance for further operations as the save operation might have changed the
     * email instance completely.
     *
     * @param customer must not be {@literal null}.
     * @return the saved email will never be {@literal null}.
     * @throws InvalidCustomerException if email is null || is detached || has invalid properties
     */
    Customer createCustomer(Customer customer);

    /**
     * Retrieves a email by his/her id.
     *
     * @param id must not be {@literal null}.
     * @return the email with the given id or null if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Customer findCustomer(Long id);

    Customer findByEmail(String email);
}
