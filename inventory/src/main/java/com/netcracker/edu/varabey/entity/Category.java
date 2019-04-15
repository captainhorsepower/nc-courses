package com.netcracker.edu.varabey.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * категория ордер айтема.
 * используется только и только для поиска
 * айтемов по категории.
 * категории можно обновить имя, больше ничего обновлять не нужно.
 */
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Getter
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @OneToMany(mappedBy = "category",
            fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private Set<OrderItem> items = new HashSet<>();

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
