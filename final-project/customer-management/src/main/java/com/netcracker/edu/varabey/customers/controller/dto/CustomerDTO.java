package com.netcracker.edu.varabey.customers.controller.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String fio;
    private Integer age;
    private String email;

    public CustomerDTO(Long id, String fio, Integer age, String email) {
        this.id = id;
        this.fio = fio;
        this.age = age;
        this.email = email;
    }
}
