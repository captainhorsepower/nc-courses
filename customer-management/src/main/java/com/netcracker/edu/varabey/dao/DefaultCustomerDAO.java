package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DefaultCustomerDAO implements CustomerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer update(Customer customer) {
        entityManager.merge(customer);
        return customer;
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = entityManager.getReference(Customer.class, id);
        entityManager.remove(customer);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> allCustomers;
        allCustomers = entityManager.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class).getResultList();
        return allCustomers;
    }
}
