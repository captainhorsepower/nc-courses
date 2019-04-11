package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Getter
    @Setter
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false, name = "tag")
    private String name;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(name = "offer_tag",
            inverseJoinColumns = @JoinColumn(name = "offer_id"),
            joinColumns = @JoinColumn(name = "tag_id"))
    private Set<Offer> offers = new HashSet<>();

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
