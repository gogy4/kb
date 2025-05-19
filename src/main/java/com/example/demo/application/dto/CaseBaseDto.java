package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CaseBaseDto {
    private long id;
    private double price;
    private List<SkinBaseDto> skins;
    private String imageUrl;
}
