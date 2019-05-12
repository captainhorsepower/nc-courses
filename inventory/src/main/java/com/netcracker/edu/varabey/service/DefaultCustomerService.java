package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.dao.CustomerDAO;
import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.service.validation.EmailValidator;
import com.netcracker.edu.varabey.service.validation.ServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    private final CustomerDAO customerDAO;
    private final ServiceValidator<Customer, Long> customerValidator;
    private final EmailValidator customerEmailValidator;

    public DefaultCustomerService(CustomerDAO customerDAO, ServiceValidator<Customer, Long> customerValidator, EmailValidator customerEmailValidator) {
        this.customerDAO = customerDAO;
        this.customerValidator = customerValidator;
        this.customerEmailValidator = customerEmailValidator;
    }

    @Override
    public Customer createCustomer(Customer c) {
        customerValidator.checkNotNull(c);
        Customer customerWithSameEmail = findByEmail(c.getEmail());
        if (customerWithSameEmail != null) {
            return customerWithSameEmail;
        }
        return customerDAO.save(c);
    }


    @Override
    public Customer findCustomer(Long id) {
        customerValidator.checkIdIsNotNull(id);
        return customerDAO.findById(id)
                .orElse(null);
    }

    @Override
    public Customer findByEmail(String email) {
        customerEmailValidator.check(email);
        return customerDAO.findByEmail(email);
    }
}
