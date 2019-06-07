package com.netcracker.edu.varabey.catalog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.StringJoiner;

@Data
@NoArgsConstructor
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "price_id")
    private Long id;

    @Column(nullable = false)
    private Double value;

    public Price(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Price.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("value=" + value)
                .toString();
    }
}
