package by.courses.vorobey.artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
public class Item {
    @Getter
    @Setter
    private int price;

    @Getter
    @Setter
    private String name;

    @Getter
    private final int itemId;
}