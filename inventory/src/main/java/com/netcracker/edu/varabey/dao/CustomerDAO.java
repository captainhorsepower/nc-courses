package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;

public interface CustomerDAO {
    Customer create(Customer customer);
    Customer find(Long id);
    Customer update(Customer customer);
    void delete(Long id);
}
