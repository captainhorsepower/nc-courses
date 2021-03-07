package com.netcracker.edu.varabey.catalog.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * offer category.
 * Categorizes offers
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE})
    private Set<Offer> offers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
