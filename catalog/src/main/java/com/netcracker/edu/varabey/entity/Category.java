package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * offer category.
 * Categorizes offers
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Getter
    @Setter
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE})
    private Set<Offer> offers = new HashSet<>();

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
