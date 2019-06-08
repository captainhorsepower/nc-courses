package com.netcracker.edu.varabey.inventory.data.dao;

import com.netcracker.edu.varabey.inventory.data.entity.*;

import java.util.Collection;
import java.util.List;

public interface OrderDAO {

    /**
     * Create Order and its orderItems in the Inventory database.
     * @param order transient order.
     * @return created in the database order.
     */
    Order save(Order order);

    /**
     * Find order in the Inventory database.
     * @param id unique order id.
     * @return found order.
     */
    Order findById(Long id);

    /**
     * Find all orders from the Inventory database.
     * @return all found orders.
     */
    List<Order> findAll();

    /**
     * Find all orders by given customer.
     * @param customer query param.
     * @return List of all orders by given customer.
     */
    List<Order> findAllByCustomer(Customer customer);

    /**
     * Find all orders by payment status.
     * @param isPaid query param
     * @return all orders with specified payment status.
     */
    List<Order> findAllByPaymentStatus(Boolean isPaid);

    @Deprecated
    Order update(Order order);

    /**
     * Delete order from the Inventory database.
     * @param id unique id of the target order.
     */
    void delete(Long id);


    List<OrderItem> findAllOrderItemsByCustomerAndTags(Customer c, Collection<Tag> tags);
    List<OrderItem> findAllOrderItemsByCustomerAndCategory(Customer cu, Category ca);
    List<OrderItem> findAllOrderItemsByCustomer(Customer customer);

    Double getTotalMoneySpendByCustomer(Customer customer);

    Long getItemCountBoughtByCustomer(Customer customer);
}
