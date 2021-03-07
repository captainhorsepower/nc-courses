package com.netcracker.edu.varabey.customers.service;

import com.netcracker.edu.varabey.customers.dao.CustomerDAO;
import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.customers.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.customers.validation.util.CustomerValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * service layer for Customer-Management module.
 * Allows to perform all required crud operations
 */
@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    private final CustomerDAO customerDAO;
    private final CustomerValidator customerValidator;

    public DefaultCustomerService(CustomerDAO customerDAO, CustomerValidator customerValidator) {
        this.customerDAO = customerDAO;
        this.customerValidator = customerValidator;
    }

    @Logged(messageBefore = "Saving customer to the database...",
            messageAfter = "Customer saved.")
    @Override
    public Customer save(Customer c) {
        customerValidator.checkForPersist(c);
        customerValidator.checkNotFoundByEmail(customerDAO.findByEmail(c.getEmail()), c.getEmail());
        return customerDAO.save(c);
    }


    @Logged(messageBefore = "Looking for customer by id in the database...",
            messageAfter = "Found customer")
    @Override
    public Customer find(Long id) {
        customerValidator.checkIdIsNotNull(id);
        return customerDAO.findById(id)
                .orElse(null);
    }

    @Logged(messageBefore = "Looking for customer by email in the database...",
            messageAfter = "Found customer")
    @Override
    public Customer findByEmail(String email) {
        customerValidator.checkEmail(email);
        return customerDAO.findByEmail(email);
    }

    @Logged(messageBefore = "Getting all customers from the database...",
            messageAfter = "Customers acquired.")
    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();

        /* dao returns iterable */
        customerDAO.findAll().forEach(list::add);
        return list;
    }


    @Logged(messageBefore = "Updating customer in the the database...",
            messageAfter = "Customer updated.")
    @Override
    public Customer update(Customer c) {
        customerValidator.checkNotNull(c);
        customerValidator.checkIdIsNotNull(c.getId());
        Customer existingCustomer = customerValidator.checkFoundById(find(c.getId()), c.getId());

        if (c.getFio() != null) {
            customerValidator.checkName(c.getFio());
            existingCustomer.setFio(c.getFio());
        }
        if (c.getAge() != null) {
            customerValidator.checkAge(c.getAge());
            existingCustomer.setAge(c.getAge());
        }
        return existingCustomer;
    }

    @Logged(messageBefore = "Deleting customer from the database...",
            messageAfter = "Customer deleted.")
    @Override
    public void delete(Long id) {
        customerValidator.checkIdIsNotNull(id);
        try {
            customerDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerException("Customer with id=" + id + " was not found. Unable to delete.");
        }
    }
}
