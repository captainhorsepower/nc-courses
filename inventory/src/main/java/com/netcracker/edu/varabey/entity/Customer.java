package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * customer, who may place orders.
 *
 * Customer's orders will be populated from database automatically via using OrderDAO.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Getter
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @NonNull
    @Getter
    @Setter
    @Column(name = "fio", nullable = false)
    private String fio;

    @NonNull
    @Getter
    @Setter
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
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("fio='" + fio + "'")
                .add("age=" + age)
                .toString();
    }
}
