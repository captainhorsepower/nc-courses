package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CustomerDAO;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.validation.AgeValidator;
import com.netcracker.edu.varabey.service.validation.EmailValidator;
import com.netcracker.edu.varabey.service.validation.NameValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import com.netcracker.edu.varabey.service.validation.exceptions.CustomerNotFoundException;
import com.netcracker.edu.varabey.service.validation.exceptions.InvalidCustomerException;
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
    private final ServiceValidator<Customer, Long> customerValidator;
    private final EmailValidator customerEmailValidator;
    private final NameValidator customerNameValidator;
    private final AgeValidator customerAgeValidator;

    public DefaultCustomerService(CustomerDAO customerDAO, ServiceValidator<Customer, Long> customerValidator, EmailValidator customerEmailValidator, NameValidator customerNameValidator, AgeValidator customerAgeValidator) {
        this.customerDAO = customerDAO;
        this.customerValidator = customerValidator;
        this.customerEmailValidator = customerEmailValidator;
        this.customerNameValidator = customerNameValidator;
        this.customerAgeValidator = customerAgeValidator;
    }

    @Override
    public Customer save(Customer c) {
        customerValidator.checkForPersist(c);
        Customer customerWithSameEmail = customerDAO.findByEmail(c.getEmail());
        if (customerWithSameEmail != null) {
            throw new InvalidCustomerException("Email " + customerWithSameEmail.getEmail() + " is already in use.");
        }
        return customerDAO.save(c);
    }


    @Override
    public Customer find(Long id) {
        customerValidator.checkIdIsNotNull(id);
        return customerDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Customer findByEmail(String email) {
        customerEmailValidator.check(email);
        return customerDAO.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();

        /* dao returns iterable */
        customerDAO.findAll().forEach(list::add);
        return list;
    }


    @Override
    public Customer update(Customer c) {
        customerValidator.checkNotNull(c);
        customerValidator.checkIdIsNotNull(c.getId());
        Customer existingCustomer = find(c.getId());
        customerValidator.checkFoundById(existingCustomer, c.getId());

        if (c.getFio() != null) {
            customerNameValidator.check(c.getFio());
            existingCustomer.setFio(c.getFio());
        }
        if (c.getAge() != null) {
            customerAgeValidator.check(c.getAge());
            existingCustomer.setAge(c.getAge());
        }
//        if (c.getEmail() != null) {
//            Customer customerWithSameEmail = findByEmail(c.getEmail());
//            if (customerWithSameEmail != null) {
//                throw new InvalidCustomerException("Email " + customerWithSameEmail.getEmail() + " is already in use.");
//            }
//            existingCustomer.setEmail(c.getEmail());
//        }

        return existingCustomer;
    }


    @Override
    public void delete(Long id) {
        customerValidator.checkIdIsNotNull(id);
        customerDAO.deleteById(id);
    }
}
