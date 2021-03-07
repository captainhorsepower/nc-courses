package by.courses.vorobey.artem.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(staticName = "of")
public class Order {

    /** unique id of this order */
    @Getter
    @Setter
    private Long orderId;

    /**
     * customer, who ordered it;
     * if id is null, then customer was removed (account deleted)
     * but order record remains for archive
     *
     * this represents one(customer) to many(orders) mapping
     */
    @Getter
    @Setter
    /*
     * store just id, instead of object,
     * because order and customer can exist separately
     * (still order requires customer to exist)
     */
    private Long customerId;


    /**
     * list of items, included in order
     * many(orders) to mane(items) mapping
     */
    @Getter
    @Setter
    private List<Item> items;

    /** date, when user checked out order */
    @Getter
    @Setter
    @NonNull
    private Date orderDate;

    /**
     * adds item to order item list, allows duplicate
     * intended for OrderDAO update()
     * @param item new item in order
     */
    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(item);
    }


    /**
     * @return accumulated price of all items in order
     */
    public float getPrice() {
        return (float)
                ((items == null || items.isEmpty())
                    ? 0.
                    : items.stream()
                        .mapToDouble(Item::getPrice)
                        .reduce(0., (a,b) -> a+b));
    }

    /**
     * @return total items in order
     */
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
            "Order : id=" + orderId
                + ", customer_id=" + customerId
                + ", date=\'" + orderDate + "\'"
                + ", item_count=" + getItemCount()
                + ", total_price=" + getPrice()
                + "\n\t items :"
        );

        items.forEach(i -> sb.append("\n\t").append(i.toString()));

        return sb.toString();
    }
}
