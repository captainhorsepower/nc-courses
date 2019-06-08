package com.netcracker.edu.varabey.inventory.data.dao;

import com.netcracker.edu.varabey.inventory.data.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DefaultOrderDAO implements OrderDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Order save(Order order) {
        /* merge(), а не persist(), т.к. айтемы вполне могут содержать
         * уже существующую (а значит detached) категорию, а значит persist()
         * кинет эксепшон */
        order = em.merge(order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        Order order = em.find(Order.class, id);
        /* without refresh list of items gets polluted with
         * some random items, god only knows why. */
        if (order != null) em.refresh(order);
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = em.createNamedQuery("Order.findAll", Order.class).getResultList();
        /* without refresh list of items gets polluted with
         * some random items, god only knows why. */
        orders.forEach(em::refresh);
        return orders;
    }

    @Override
    public List<Order> findAllByCustomer(Customer customer) {
        return em.createNamedQuery("Order.findAllByCustomer", Order.class)
                .setParameter("customerId", customer.getId())
                .getResultList();
    }

    @Override
    public List<Order> findAllByPaymentStatus(Boolean isPaid) {
        return em.createNamedQuery("Order.findAllByPaymentStatus", Order.class)
                .setParameter("isPaid", isPaid)
                .getResultList();
    }

    @Deprecated
    @Override
    public Order update(Order order) {
        return em.merge(order);
    }

    @Override
    public void delete(Long id) {
        Order o = em.getReference(Order.class, id);
        em.remove(o);
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndTags(Customer customer, Collection<Tag> tags) {
        return em.createNamedQuery("OrderItem.findAllByCustomerAndTags", OrderItem.class)
                .setParameter("customerId", customer.getId())
                .setParameter("tagNameList", tags.stream().map(Tag::getName).collect(Collectors.toList()))
                .setParameter("tagCount", (long) tags.size())
                .getResultList();
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca) {
        return em.createNamedQuery("OrderItem.findAllByCustomerAndCategory", OrderItem.class)
                .setParameter("category_id", ca.getId())
                .setParameter("customer_id", cu.getId())
                .getResultList();
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCustomer(Customer customer) {
        return em.createNamedQuery("OrderItem.findAllByCustomer", OrderItem.class)
                .setParameter("customer_id", customer.getId())
                .getResultList();
    }
}