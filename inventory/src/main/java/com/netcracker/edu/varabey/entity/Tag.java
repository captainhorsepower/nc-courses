package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Тэги, в этом модуле могут быть (и будут) общие даже у ордерайтемов
 * из разных заказов, т.к. так искать по тэгу удобнее и эффективнее
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {

    @Getter
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<OrderItem> items = new HashSet<>();

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }

    /* hashCode && equals для сета */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getName().equals(tag.getName());
    }
}
