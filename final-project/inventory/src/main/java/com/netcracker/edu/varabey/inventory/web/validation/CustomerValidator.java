package com.netcracker.edu.varabey.inventory.web.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;

public interface CustomerValidator extends ServiceValidator<Customer, Long> {
    void checkEmail(String email);
    void checkForPersist(Customer customer);
    Customer checkFoundByEmail(Customer customer, String email);
}
