package com.netcracker.edu.varabey.customers.validation;

import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.customers.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.customers.validation.exceptions.CustomerException;
import com.netcracker.edu.varabey.customers.validation.util.AgeValidator;
import com.netcracker.edu.varabey.customers.validation.util.CustomerValidator;
import com.netcracker.edu.varabey.customers.validation.util.EmailValidator;
import com.netcracker.edu.varabey.customers.validation.util.NameValidator;
import org.springframework.http.HttpStatus;

@Validator
public class DefaultCustomerValidator implements CustomerValidator {
    private final NameValidator nameValidator;
    private final AgeValidator ageValidator;
    private final EmailValidator emailValidator;

    public DefaultCustomerValidator(NameValidator customerNameValidator, AgeValidator customerAgeValidator, EmailValidator customerEmailValidator) {
        this.nameValidator = customerNameValidator;
        this.ageValidator = customerAgeValidator;
        this.emailValidator = customerEmailValidator;
    }

    @Override
    public void checkNotNull(Customer c) {
        if (c == null) {
            throw new CustomerException("Customer must not be null");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new CustomerException("Customer's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new CustomerException("Customer's id must be NOT null");
        }
    }

    @Logged(messageBefore = "Verifying customer's name...")
    @Override
    public void checkName(String name) {
        nameValidator.check(name);
    }

    @Logged(messageBefore = "Verifying customer's age...")
    @Override
    public void checkAge(Integer age) {
        ageValidator.check(age);
    }

    @Logged(messageBefore = "Verifying customer's email...")
    @Override
    public void checkEmail(String email) {
        emailValidator.check(email);
    }

    @Override
    public Customer checkFound(Customer customer, String notFoundMessage) {
        if (customer == null) {
            throw new CustomerException(notFoundMessage, HttpStatus.NOT_FOUND);
        }
        return customer;
    }

    @Logged(messageBefore = "Making sure customer is found by email...")
    @Override
    public Customer checkFoundByEmail(Customer customer, String email) {
        return checkFound(customer, "Customer with email=\'" + email + "\' was not found");
    }

    @Logged(messageBefore = "Making sure customer is found by id...")
    @Override
    public Customer checkFoundById(Customer customer, Long id) {
        return checkFound(customer, "Customer with id=" + id + " was not found");
    }

    @Logged(messageBefore = "Making sure email is not used by another customer...")
    @Override
    public void checkNotFoundByEmail(Customer customer, String email) {
        if (customer != null) {
            throw new CustomerException("Email=\'" + email + "\' is already used by another person");
        }
    }

    @Override
    public void checkAllProperties(Customer customer) {
        checkName(customer.getFio());
        checkAge(customer.getAge());
        checkEmail(customer.getEmail());
    }

    @Override
    public void checkForPersist(Customer customer) {
        checkNotNull(customer);
        checkIdIsNull(customer.getId());
        checkAllProperties(customer);
    }
}