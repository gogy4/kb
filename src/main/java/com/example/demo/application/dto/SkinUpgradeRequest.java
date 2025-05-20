package com.example.demo.application.dto;

import lombok.Data;

@Data
public class SkinUpgradeRequest {
    private SkinDto currentSkin;
    private SkinDto newSkin;
}
