package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.Customer;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class DefaultCustomerService implements CustomerService {
    private EntityManagerFactory emf = PostgreSQLDatabaseEntityManagerFactory.getInstance();

    @Override
    public Customer create(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
        return customer;
    }

    @Override
    public Customer read(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, id);
        if (customer != null) em.refresh(customer);
        em.getTransaction().commit();
        em.close();
        return customer;
    }

    private Customer update(Customer c, EntityManager em) {
        Query q = em.createQuery("SELECT count(c.id) FROM Customer c where c.id = :id");
        q.setParameter("id", c.getId());
        long persistedCustomerCount = (Long) q.getSingleResult();
        if (persistedCustomerCount == 0) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("unable to update Customer, that is not stored in database");
        }

        c = em.merge(c);
        return  c;
    }

    /** позволит сменить имя и возраст кастомера */
    @Override
    public Customer update(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        customer = update(customer, em);
        em.getTransaction().commit();
        em.close();
        return customer;
    }

    /** удалить кастомера и все связанные с ним ордеры из бд */
    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer c = em.getReference(Customer.class, id);
        em.remove(c);
        em.getTransaction().commit();
        em.close();
    }
}
