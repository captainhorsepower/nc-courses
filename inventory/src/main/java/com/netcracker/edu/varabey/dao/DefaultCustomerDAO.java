package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DefaultCustomerDAO implements CustomerDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Customer create(Customer customer) {
        em.persist(customer);
        return customer;
    }

    @Override
    public Customer find(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) em.refresh(customer);
        return customer;
    }

    /** позволит сменить имя и возраст кастомера */
    @Override
    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    /** удалить кастомера и все связанные с ним ордеры из бд */
    @Override
    public void delete(Long id) {
        Customer c = em.getReference(Customer.class, id);
        em.remove(c);
    }
}
