package by.courses.vorobey.artem.entity;

import lombok.*;

@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
public class Item {

    /** item id (primary key int items table) */
    @Getter
    @Setter
    private int itemId;

    /** item name */
    @Getter
    @Setter
    @NonNull
    private String name;

    /** item price */
    @Getter
    @Setter
    @NonNull
    private float price;

    @Override
    public String toString() {
        return "Item : id=" + itemId
                + ", name=\'" + name.trim() + "\'"
                + ", price=" + price + ";";
    }
}