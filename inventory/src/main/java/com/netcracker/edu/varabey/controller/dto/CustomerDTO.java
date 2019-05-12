package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String email;

    public CustomerDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
