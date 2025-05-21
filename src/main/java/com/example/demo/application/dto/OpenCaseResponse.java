package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenCaseResponse {
    private SkinDto skin;
    private double balance;
}
