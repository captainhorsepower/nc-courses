package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public Customer save(Customer c) {
        return customerDAO.save(c);
    }

    public Customer update(Customer c) {
        return customerDAO.update(c);
    }

    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    public Customer findById(Long id) {
        return customerDAO.findById(id);
    }

    public void delete(Long id) {
        customerDAO.deleteById(id);
    }
}
