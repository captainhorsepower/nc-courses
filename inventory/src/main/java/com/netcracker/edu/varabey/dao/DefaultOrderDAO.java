package com.netcracker.edu.varabey.dao;

import com.netcracker.edu.varabey.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DefaultOrderDAO implements OrderDAO {
    @PersistenceContext
    private EntityManager em;

    /** записать оффер и входящие в него айтемы в б.д. */
    @Override
    public Order save(Order order) {
        /* merge(), а не persist(), т.к. айтемы вполне могут содержать
         * уже существующую (а значит detached) категорию, а значит persist()
         * кинет эксепшон */
        order = em.merge(order);
        return order;
    }

    @Override
    public Order find(Long id) {
        Order order = em.find(Order.class, id);
        /* without refresh list of items gets polluted with
         * some random items, god only knows why.
         */
        if (order != null) em.refresh(order);
        return order;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Order> findAll() {
        List<Order> orders = (List<Order>) em.createQuery("SELECT o"
                + " FROM Order o").getResultList();
        /* without refresh list of items gets polluted with
         * some random items, god only knows why.
         */
        orders.forEach(em::refresh);
        return orders;
    }

    @Override
    public Order update(Order order) {
        return em.merge(order);
    }

    @Override
    public void delete(Long id) {
        Order o = em.getReference(Order.class, id);
        em.remove(o);
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTag(Customer c, Tag t) {

        /* I'm sure it's for from the most optimal way, but for now at least it works */
        Query q = em.createQuery("SELECT i "
                + " FROM OrderItem i, Tag t "
                + " WHERE t.id=:tag_id AND "
                + " i.owningOrder.customer.id=:customer_id AND"
                + " i MEMBER OF t.items"
        );

        q.setParameter("tag_id", t.getId());
        q.setParameter("customer_id", c.getId());

        return (List<OrderItem>) q.getResultList();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca) {
        Query q = em.createQuery("SELECT i "
                + " FROM OrderItem i "
                + " INNER JOIN Category ca ON i MEMBER OF ca.items"
                + " WHERE i.owningOrder.customer.id=:customer_id "
                + " AND ca.id=:category_id"
        );

        q.setParameter("category_id", ca.getId());
        q.setParameter("customer_id", cu.getId());

        return (List<OrderItem>) q.getResultList();
    }
}