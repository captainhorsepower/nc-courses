package by.courses.vorobey.artem.entity;

import lombok.*;

import java.sql.Date;

@RequiredArgsConstructor(staticName = "of")
public class Customer {

    @Getter
    @Setter
    private int customerId;

    @Getter
    @Setter
    @NonNull
    private String nickname;

    @Getter
    @Setter
    @NonNull
    private Date birthday;

    @Getter
    @NonNull
    private final Address address;

}
