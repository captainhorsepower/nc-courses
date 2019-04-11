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

    /**
     * unique category name
     */
    @Getter
    @Setter
    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    /**
     * all offers, that are are inside this category;
     */
    /*
     * delete all offers of this category on delete
     * therefore, update them before delete,
     * to avoid detached state issues
     *
     * Set, because offer can be included in category only once
     */
    @Getter
    @Setter
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE, CascadeType.DETACH})
    private Set<Offer> offers = new HashSet<>();

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
