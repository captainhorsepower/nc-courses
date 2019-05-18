package com.netcracker.edu.varabey.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderInputDTO {
    private String email = "";
    private String orderStatus = "";
    private Boolean isPaid = false;
    private LocalDateTime createdOnDate = LocalDateTime.now();
    private Set<Long> offerIds = new HashSet<>();

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
