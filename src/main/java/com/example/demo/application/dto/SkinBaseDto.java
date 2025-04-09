package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class SkinBaseDto {
    private long id;
    private double price;
}
