package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class CaseBaseInfo {
    private long id;
    private double price;
    private List<SkinBaseDto> skins;
}
