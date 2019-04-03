package by.courses.vorobey.artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class Order {

    @Getter
    private final int customerId;

    @Getter
    private final int orderId;

    @Getter
    private int price;

    @Getter
    private final List<Item> items;
}
