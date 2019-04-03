package by.courses.vorobey.artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
public class Address {

    @Getter
    private final int addressId;

    @Getter
    private final int customerId;

    @Getter
    @Setter
    private String address;
}
