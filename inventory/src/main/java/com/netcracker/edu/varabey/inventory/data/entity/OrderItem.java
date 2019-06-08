package com.netcracker.edu.varabey.inventory.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * ордер айтем - это снапшот оффера, создаётся, когда его добавляют в заказ,
 * удаляется, когда его удаляют из заказа.
 *
 * можно измнеть пока можно изсменять заказ (пока он не оплачен).
 *
 * т.к. изменять ордерайтем можно только пока можно изменять ордер,
 * то и изменять его нужно посредством ордера и OrderDAO.
 * (см. Order для подробностей по обновлению ордер айтема
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
@NamedQueries({
        @NamedQuery(name = "OrderItem.findAllByCustomer", query = "SELECT item FROM OrderItem item WHERE item.owningOrder.customer.id = :customer_id"),
        @NamedQuery(name = "OrderItem.findAllByCustomerAndCategory", query = "SELECT item FROM OrderItem item  INNER JOIN Category cat ON item MEMBER OF cat.items WHERE item.owningOrder.customer.id=:customer_id  AND cat.id=:category_id"),
        @NamedQuery(name = "OrderItem.findAllByCustomerAndTags", query = "SELECT item FROM OrderItem item INNER JOIN Tag tag ON item MEMBER OF tag.items WHERE item.owningOrder.customer.id = :customerId AND tag.name IN (:tagNameList)  GROUP BY item.id HAVING COUNT(DISTINCT tag.name) = :tagCount"),
})
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToOne (optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;

    @Column(nullable = false)
    private String name;

    /**
     * так как ордерайтем это снапшот оффера, то один и тот же оффер
     * в разных заказах - это разный ордер айтем, в том числе они могут различаться
     * ценой (инфляция все дела).
     * по сему есть вот эта строгая привязка к ордеру.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order owningOrder;

    /* категория будет задаваться из соотвествующего оффера из каталога,
     * т.е. создаздать категорию в дао -> создать айтем с категорией */
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;


    /* в какскадах только мерж, т.к. создаются айтемы с помощью ордера,
     * а он использует вместо persist() merge() */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "item_tag",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    /* несколько удобных конструкторов */
    public OrderItem(Double price, String name) {
        setPriceByValue(price);
        this.name = name;
    }

    /* несколько удобных конструкторов */
    public OrderItem(Double price, String name, Category c) {
        setPriceByValue(price);
        this.name = name;
        this.category = c;
    }

    /** set price value */
    public void setPriceByValue(Double value) {
        price = new Price(value);
    }

    /** неизменяемы лист тэгов, нужне для их простотра. */
    public List<Tag> getTags() {
        return Collections.unmodifiableList(new ArrayList<>(tags));
    }

    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * создаёт клона айтема, но без установленного id.
     * нужен для добавления за один запрос нескольких дупликатов
     * айтемов в ордер.
     * @param item оригинал
     * @return item копия
     */
    public static OrderItem getDuplicate(OrderItem item) {
        OrderItem duplicate = new OrderItem(item.getPrice().getValue(), item.getName(), item.getCategory());
        item.getTags().forEach(duplicate::addTag);
        return duplicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(price, orderItem.price) &&
                Objects.equals(name, orderItem.name) &&
                Objects.equals(category, orderItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, category);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderItem.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("price=" + price)
                .add("name='" + name + "'")
                .add("order_id=" +owningOrder.getId())
                .toString();
    }
}