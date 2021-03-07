package com.netcracker.edu.varabey.customers.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_fio", nullable = false)
    private String fio;

    @Column(name = "customer_age", nullable = false)
    private Integer age;

    @Column(name ="email", nullable = false)
    private String email;

    public Customer(String fio, Integer age, String email) {
        this.fio = fio;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("fio='" + fio + "'")
                .add("age=" + age)
                .add("email='" + email + "'")
                .toString();
    }
}
