package by.courses.vorobey.artem.entity;

import lombok.*;

@RequiredArgsConstructor(staticName = "of")
public class Address {

    /*
     * address_id in customers table.
     * Setter should only be called from CustomerDao,
     * when new customer is added to db ( to set his resulting id )
     */
    @Getter
    @Setter
    private int addressId;

    /**
     * customer, which uses this address.
     * Is set after customer is added to db;
     * Is represented by customer_id in addresses table;
     * Used for one to one mapping;
     */
    @Getter
    @Setter
    private Customer customer;

    /* some addr fields... */
    @Getter
    @Setter
    @NonNull // lombok annotation for @RequiredArgsConstructor
    private String city;

    @Getter
    @Setter
    @NonNull
    private String street;

    @Getter
    @Setter
    @NonNull
    private int buildNumber;

    @Override
    public String toString() {
        return "Address : "
                + "city=\'" + city + "\'"
                + ", street=\'" + street + "\'"
                + ", build_number=" + buildNumber
                + ", addr_id=" + addressId
                + ", cust_id=" + customer.getCustomerId();
    }
}
