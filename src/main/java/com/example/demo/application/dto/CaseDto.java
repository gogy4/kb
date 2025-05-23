package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CaseDto {
    private long id;
    private double price;
    private List<SkinDto> skins;
    private String imageUrl;
    private String title;
}
