package by.courses.vorobey.artem.entity;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
public class Customer {

    /*
     * customer_id in customers table.
     * Setter should only be called from CustomerDao,
     * when new customer is added to db ( to set his resulting id )
     */
    @Getter
    @Setter
    private Long customerId;

    /** unique username in db */
    @Getter
    @Setter
    @NonNull
    private String nickname;

    /** customer birthday date */
    @Getter
    @Setter
    @NonNull
    private Date birthday;

    /**
     * address where this customer's orders should be delivered.
     * represents one to one mapping (one customer to one address)
     */
    @Getter
    @NonNull
    private final Address address;

    @Override
    public String toString() {
        return "Customer : "
                + "nick=\'" + nickname + "\'"
                + ", birthday=\'" + birthday + "\'"
                + ", cust_id=" + customerId
                + ", addr_id=" + address.getAddressId()
                + ",\n\t" + address;
    }
}
