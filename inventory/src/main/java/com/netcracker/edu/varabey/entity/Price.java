package com.netcracker.edu.varabey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/** полностью аналогично цене из каталога */
@Data
@NoArgsConstructor
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return getValue().equals(price.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
