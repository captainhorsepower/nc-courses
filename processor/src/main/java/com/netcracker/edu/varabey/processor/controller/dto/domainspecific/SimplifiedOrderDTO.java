package com.netcracker.edu.varabey.processor.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @JsonIgnore
    public Boolean isPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
