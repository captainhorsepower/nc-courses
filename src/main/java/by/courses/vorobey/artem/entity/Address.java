package by.courses.vorobey.artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
public class Address {

    @Getter
    @Setter
    private int addressId;

    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private int buildNumber;
}
