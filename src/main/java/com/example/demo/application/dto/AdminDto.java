package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDto {
    private Long id;
    private String email;
    private String password;
}
