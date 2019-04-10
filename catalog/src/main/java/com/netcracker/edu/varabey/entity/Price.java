package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "prices")
@NoArgsConstructor
@RequiredArgsConstructor
public class Price {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "price_id")
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false)
    private Double value;

    @Override
    public String toString() {
        return new StringJoiner(", ", Price.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("value=" + value)
                .toString();
    }
}
