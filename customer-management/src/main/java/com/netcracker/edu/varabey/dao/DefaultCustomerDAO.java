package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DefaultCustomerDAO implements CustomerDAO {
    private EntityManager entityManager =
            PostgreSQLDatabaseManager.getInstance().getEntityManager();

    @Override
    public Customer create(Customer customer) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(customer);
        tx.commit();
        return customer;
    }

    @Override
    public Customer read(Long id) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Customer customer = entityManager.find(Customer.class, id);
        tx.commit();
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.merge(customer);
        tx.commit();
        return customer;
    }

    @Override
    public void delete(Customer customer) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.remove(customer);
        tx.commit();
    }

    @Override
    public List<Customer> readAll() {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        List<Customer> allCustomers;
        allCustomers = entityManager.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class).getResultList();
        tx.commit();
        return allCustomers;
    }
}
