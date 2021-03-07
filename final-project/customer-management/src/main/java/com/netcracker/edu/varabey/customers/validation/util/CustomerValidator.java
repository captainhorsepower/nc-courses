package com.netcracker.edu.varabey.customers.validation.util;

import com.netcracker.edu.varabey.customers.entity.Customer;
import com.netcracker.edu.varabey.customers.validation.ServiceValidator;

public interface CustomerValidator extends ServiceValidator<Customer, Long> {
    void checkName(String name);
    void checkAge(Integer age);
    void checkEmail(String email);
    void checkForPersist(Customer customer);
    Customer checkFoundByEmail(Customer customer, String email);
    void checkNotFoundByEmail(Customer customer, String email);
}
