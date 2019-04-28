package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;

/* не использую CrudRepository, т.к. это немного ломает find */
public interface CustomerDAO {
    Customer save(Customer customer);
    Customer find(Long id);
    Customer update(Customer customer);
    void delete(Long id);
}
