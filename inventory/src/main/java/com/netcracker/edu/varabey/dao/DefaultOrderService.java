package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.*;
import com.netcracker.edu.varabey.utils.PostgreSQLDatabaseEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DefaultOrderService implements OrderService {
    private EntityManagerFactory emf = PostgreSQLDatabaseEntityManagerFactory.getInstance();

    /** записать оффер и входящие в него айтемы в б.д. */
    @Override
    public Order create(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* merge(), а не persist(), т.к. айтемы вполне могут содержать
         * уже существующую (а значит detached) категорию, а значит persist()
         * кинет эксепшон */
        order = em.merge(order);
        em.getTransaction().commit();
        em.close();
        return order;
    }

    @Override
    public Order read(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order order = em.find(Order.class, id);

        /* without refresh list of items gets polluted with
         * some random items, god only knows why.
         */
        if (order != null) em.refresh(order);
        em.getTransaction().commit();
        em.close();
        return order;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Order> readAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Order> orders = (List<Order>) em.createQuery("SELECT o"
                + " FROM Order o").getResultList();

        /* without refresh list of items gets polluted with
         * some random items, god only knows why.
         */
        orders.forEach(em::refresh);
        em.getTransaction().commit();
        em.close();
        return orders;
    }

    private Order update(Order o, EntityManager em) {
        Query q = em.createQuery("SELECT count(o.id) FROM Order o where o.id = :id");
        q.setParameter("id", o.getId());
        long persistedOrderCount = (Long) q.getSingleResult();
        if (persistedOrderCount == 0) {
            em.getTransaction().rollback();
            em.close();
            throw new IllegalArgumentException("unable to update Order, that is not stored in database");
        }

        o = em.merge(o);
        return  o;
    }

    @Override
    public Order update(Order order) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        order = update(order, em);
        em.getTransaction().commit();
        em.close();
        return order;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order o = em.getReference(Order.class, id);
        em.remove(o);
        em.getTransaction().commit();
        em.close();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer c, Tag t) {
        List<OrderItem> items = new ArrayList<>();
        if (c == null || c.getId() == null || t == null || t.getId() == null){
            return items;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* I'm sure it's for from the most optimal way, but for now at least it works */
        Query q = em.createQuery("SELECT i "
                + " FROM OrderItem i, Tag t "
                + " WHERE t.id=:tag_id AND "
                + " i.owningOrder.customer.id=:customer_id AND"
                + " i MEMBER OF t.items"
        );

        q.setParameter("tag_id", t.getId());
        q.setParameter("customer_id", c.getId());

        items = (List<OrderItem>) q.getResultList();

        em.getTransaction().commit();
        em.close();
        return items;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca) {
        List<OrderItem> items = new ArrayList<>();
        if (cu == null || cu.getId() == null || ca == null || ca.getId() == null){
            return items;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery("SELECT i "
                + " FROM OrderItem i "
                + " INNER JOIN Category ca ON i MEMBER OF ca.items"
                + " WHERE i.owningOrder.customer.id=:customer_id "
                + " AND ca.id=:category_id"
        );

        q.setParameter("category_id", ca.getId());
        q.setParameter("customer_id", cu.getId());

        items = (List<OrderItem>) q.getResultList();

        em.getTransaction().commit();
        em.close();
        return items;
    }
}