package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CustomerDAO;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.validation.InvalidCustomerException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * service layer for Customer-Management module.
 * Allows to perform all required crud operations
 */
@Service
public class DefaultCustomerManagementService implements CustomerManagementService {
    private final CustomerDAO customerDAO;

    /* autowired constructor */
    public DefaultCustomerManagementService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Saves a given customer.
     * @param c new customer with valid fields
     * @return saved customer
     * @throws InvalidCustomerException if customer is null || managed || detached
     */
    @Override
    public Customer save(Customer c) throws InvalidCustomerException {
        if (c == null || c.getId() != null
            || c.getFio() == null
            || c.getAge() == null || c.getAge() < 1) {
            throw new InvalidCustomerException();
        }
        return customerDAO.save(c);
    }

    /**
     * Retrieves a customer by his(her) id
     * @param id - must not be null
     * @return found Customer or null customer wasn't found
     */
    @Override
    public Customer find(Long id) {
        return customerDAO.findById(id)
                .orElse(null);
    }

    /**
     * @return list of all Customers (or empty list)
     */
    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();

        /* dao returns iterable */
        customerDAO.findAll().forEach(list::add);
        return list;
    }

    /**
     * Updates customer with given id (must not be null)
     * @param c customer with updates
     * @return updated customer
     * @throws InvalidCustomerException if any customer field is null or if customer is a new entity
     */
    @Override
    public Customer update(Customer c) throws InvalidCustomerException {
        if (c == null || c.getId() == null || !customerDAO.existsById(c.getId())
                || c.getFio() == null
                || c.getAge() == null || c.getAge() < 1) {
            throw new InvalidCustomerException();
        }
        return customerDAO.save(c);
    }

    /**
     * @param id - id of the target customer
     */
    @Override
    public void delete(Long id) {
        customerDAO.deleteById(id);
    }
}
