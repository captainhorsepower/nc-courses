package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDAO extends CrudRepository<Customer, Long> { }
