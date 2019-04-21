package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer create(Customer customer);
    Customer read(Long id);
    Customer update(Customer customer);
    void delete(Customer customer);
    List<Customer> readAll();
}
