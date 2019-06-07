package com.netcracker.edu.varabey.processor.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class NewOrderDTO {
    private String email;
    private String orderStatus;
    private Boolean isPaid;
    private LocalDateTime createdOnDate = LocalDateTime.now();
    private Set<Long> offerIds = new HashSet<>();

    @JsonIgnore
    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
