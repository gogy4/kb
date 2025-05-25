package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SellSkinResponse {
    private UserDto user;
    private double balance;
    private List<SkinDto> skins;
}
