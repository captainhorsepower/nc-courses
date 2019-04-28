package com.netcracker.edu.varabey.service;

import com.netcracker.edu.varabey.entity.Customer;

import java.util.List;

public interface CustomerManagementService {
    Customer save(Customer c);
    Customer update(Customer c);
    List<Customer> findAll();
    Customer find(Long id);
    void delete(Long id);
}
