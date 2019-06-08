package com.netcracker.edu.varabey.inventory.data.service;

import com.netcracker.edu.varabey.inventory.data.dao.CustomerDAO;
import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import com.netcracker.edu.varabey.inventory.data.validation.CustomerValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    private final CustomerDAO customerDAO;
    private final CustomerValidator customerValidator;

    public DefaultCustomerService(CustomerDAO customerDAO, CustomerValidator customerValidator) {
        this.customerDAO = customerDAO;
        this.customerValidator = customerValidator;
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
        customerValidator.checkEmail(email);
        return customerDAO.findByEmail(email);
    }
}
