package com.netcracker.edu.varabey.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.edu.varabey.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.controller.dto.OrderItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VerboseOrderDTO {
    private Long id;
    private CustomerDTO customer;
    private String orderStatus;
    private Boolean isPaid;
    private LocalDateTime createdOnDate;
    private Double totalPrice;
    private Integer itemCount;
    private List<OrderItemDTO> items = new ArrayList<>();

    @JsonIgnore
    public Boolean isPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
