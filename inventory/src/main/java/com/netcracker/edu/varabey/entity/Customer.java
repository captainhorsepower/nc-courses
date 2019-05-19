package com.netcracker.edu.varabey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * email, who may place orders.
 *
 * Customer's orders will be populated from database automatically via using OrderDAO.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    /* получить неизменяемый лист оредеров. Изменить, удалить или добавить ордер
     * клиенту можно и нужно с помщью OrderDAO. */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(new ArrayList<>(orders));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email=" + email)
                .toString();
    }
}
