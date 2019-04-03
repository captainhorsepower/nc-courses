package by.courses.vorobey.artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
public class Customer {
    @Getter
    @Setter
    private String name;
    @Getter
    private final int customerId;
}
