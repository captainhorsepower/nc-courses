package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        if (c == null || c.getId() == null || !customerDAO.existsById(c.getId())) {
            throw new IllegalArgumentException("illegal customer passed to update");
        }
        return customerDAO.save(c);
    }

    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<>();
        customerDAO.findAll().forEach(list::add);
        return list;
    }

    public Customer findById(Long id) {
        return customerDAO.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long id) {
        customerDAO.deleteById(id);
    }
}
