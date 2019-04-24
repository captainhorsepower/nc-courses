package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer save(Customer customer);
    Customer findById(Long id);
    Customer update(Customer customer);
    void deleteById(Long id);
    List<Customer> findAll();
}
