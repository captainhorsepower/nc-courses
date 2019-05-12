package com.netcracker.edu.varabey.controller.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String fio;
    private Integer age;

    public CustomerDTO(Long id, String fio, Integer age) {
        this.id = id;
        this.fio = fio;
        this.age = age;
    }
}
