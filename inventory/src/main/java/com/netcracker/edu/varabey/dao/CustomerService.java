package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;

public interface CustomerService {
    Customer create(Customer customer);
    Customer read(Long id);
    Customer update(Customer customer);
    void delete(Long id);
}
