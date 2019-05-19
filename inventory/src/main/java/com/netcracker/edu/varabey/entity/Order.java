package com.netcracker.edu.varabey.entity;

import com.netcracker.edu.varabey.entity.utils.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Order entity. Stores information about the order.
 * The most important entity in inventory module.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /* OrderItem shouldn't exist without order */
    @OneToMany(mappedBy = "owningOrder", orphanRemoval = true,
            cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, name = "created_on")
    private LocalDateTime createdOnDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "order_status")
    private OrderStatus status = OrderStatus.APPROVED;

    @Column(nullable = false, name = "is_paid")
    private Boolean isPaid = false;

    public Order(Customer c, LocalDateTime d) {
        this.customer = c;
        if (d != null) {
            this.createdOnDate = d;
        }
    }

    /**
     * получить неизменяемый лист айтемов, который использовать ознакомления с
     * содержимым ордера.
     * Можно добавить айтем, с помощью addItem()
     * Можно получить из листа айтем и его удалить с помощью removeItem()
     * Для изменения ордер айтема, нужно получить ссылку на него из этого листа,
     * внести изменения и обновить order с помощью OrderDAO
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /** add item to the order */
    public void addItem(OrderItem item) {
        item = OrderItem.getDuplicate(item);
        item.setOwningOrder(this);
        this.items.add(item);
    }

    /** removeItem one item from the order, if it's present */
    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    /** removeItem all instances of item from order, if any */
    public void removeAllItems(OrderItem item) {
        while (this.items.remove(item));
    }

    /** removeItem all items that satisfy given predicate, e.g. p.getPrice().getValue() > 100.55 */
    public void removeAllItems(Predicate<OrderItem> p) {
        this.items = this.items.stream()
                .filter( i -> !p.test(i))
                .collect(Collectors.toList());
    }

    /** accumulated order price */
    public Double getTotalPrice() {
        return this.items.stream()
                .mapToDouble(i -> i.getPrice().getValue())
                .reduce( 0., (sum, price) -> sum += price);
    }

    /** number of items in order */
    public Integer getItemCount() {
        return this.items.size();
    }

    public void setPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean isPaid() {
        return this.isPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getCustomer(), order.getCustomer()) &&
                Objects.equals(getCreatedOnDate(), order.getCreatedOnDate()) &&
                getStatus() == order.getStatus() &&
                Objects.equals(getIsPaid(), order.getIsPaid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreatedOnDate(), getItemCount());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("createdOnDate=" + createdOnDate)
                .add("email=" + customer)
                .add("orderItemList=" + items)
                .toString();
    }
}
