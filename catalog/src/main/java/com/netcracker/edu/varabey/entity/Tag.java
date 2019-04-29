package com.netcracker.edu.varabey.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Tag, that can be attached to offers.
 * To findById a fully functional tag, initialize new tag with
 * a unique name, findById it in database via service, use.
 *
 * You cat trust the Id, name, and ids and names of offers
 * that you can get via getters. But to get all tags of any offer,
 * findById thad offer from database via corresponding service first.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @NonNull
    @Column(nullable = false, name = "tag", unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<Offer> offers = new HashSet<>();

    public Tag(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /* name should be is unique, so it should be just fine */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getName().equals(tag.getName());
    }
}
