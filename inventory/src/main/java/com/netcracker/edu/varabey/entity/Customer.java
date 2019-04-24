package com.netcracker.edu.varabey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * customer, who may place orders.
 *
 * Customer's orders will be populated from database automatically via using OrderDAO.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @NonNull
    @Column(name = "fio", nullable = false)
    private String fio;

    @NonNull
    private Integer age;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    /**
     * получить неизменяемый лист оредеров. Изменить, удалить или добавить ордер
     * клиенту можно и нужно с помщью OrderDAO.
     */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(fio, customer.fio) &&
                Objects.equals(age, customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fio, age);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("fio='" + fio + "'")
                .add("age=" + age)
                .toString();
    }
}
