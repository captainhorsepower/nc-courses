package com.netcracker.edu.varabey.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * offer entity class.
 * Stores information about name, tags, category and price.
 *
 * Any field, that you can get by getter, of an initialized
 * (created or findById via service) offer, is valid. Offers, stored in
 * category are not loaded until you explicitly load them via
 * corresponding service.
 *
 */
@Data
@javax.persistence.Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "offer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne (optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "offer_tag",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    /**
     * add tag to the offer.
     * Tag are distinguished by their name.
     *
     * @param tag to be added
     * @return false, if tag was already present in offer.
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    /**
     * remove tag from offer
     * @param tag true, of offer contained that tag.
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Offer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("price=" + price)
                .add("name=" + name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id) &&
                Objects.equals(name, offer.name) &&
                Objects.equals(price, offer.price) &&
                Objects.equals(category, offer.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category);
    }
}
