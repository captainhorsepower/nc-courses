package com.netcracker.edu.varabey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    @NonNull
    @Column(name = "customer_fio", nullable = false)
    private String fio;

    @NonNull
    @Column(name = "customer_age", nullable = false)
    private Integer age;

    public Customer(@NonNull String fio, @NonNull Integer age) {
        this.fio = fio;
        this.age = age;
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
