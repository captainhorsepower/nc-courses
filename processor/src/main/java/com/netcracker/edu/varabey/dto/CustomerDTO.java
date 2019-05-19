package com.netcracker.edu.varabey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String email;
    private String fio;
    private int age;
}
