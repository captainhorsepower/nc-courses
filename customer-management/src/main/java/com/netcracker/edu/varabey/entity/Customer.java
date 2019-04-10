package com.netcracker.edu.varabey.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long id;

    @NonNull
    @Getter
    @Setter
    @Column(name = "customer_fio")
    private String fio;

    @NonNull
    @Getter
    @Setter
    @Column(name = "customer_age")
    private int age;

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("fio='" + fio + "'")
                .add("age=" + age)
                .toString();
    }
}
