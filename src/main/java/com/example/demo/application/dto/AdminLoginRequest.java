package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginRequest {
    private String email;
    private String password;
}
