package com.netcracker.edu.varabey.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SimplifiedOrderDTO {
    private Long id;
    private String email;
    private Boolean isPaid;
    private String orderStatus;
    private LocalDateTime createdOnDate;
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
