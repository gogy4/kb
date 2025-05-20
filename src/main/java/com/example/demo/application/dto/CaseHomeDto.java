package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CaseHomeDto {
    private long id;
    private double price;
    private String imageUrl;
    private String title;
}
