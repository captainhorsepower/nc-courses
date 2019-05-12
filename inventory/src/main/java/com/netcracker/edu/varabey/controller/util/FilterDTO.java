package com.netcracker.edu.varabey.controller.util;

import lombok.Data;

@Data
public class FilterDTO {
    private Long customerId;
    private String email;
    private String tag;
    private String category;
}
