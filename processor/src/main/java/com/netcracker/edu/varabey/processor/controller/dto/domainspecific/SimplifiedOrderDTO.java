package com.netcracker.edu.varabey.processor.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SimplifiedOrderDTO {
    private Long id;
    private String email;
    private Boolean isPaid = false;
    private String orderStatus = "PENDING_APPROVAL";
    private LocalDateTime createdOnDate = LocalDateTime.now();
    private Double totalPrice;
    private Integer itemCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Long> offerIds = new HashSet<>();

    @JsonIgnore
    public Boolean isPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
