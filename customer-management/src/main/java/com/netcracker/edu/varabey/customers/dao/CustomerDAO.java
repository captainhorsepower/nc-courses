package com.netcracker.edu.varabey.customers.dao;

import com.netcracker.edu.varabey.customers.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDAO extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
}
