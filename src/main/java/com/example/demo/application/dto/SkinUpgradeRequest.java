package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkinUpgradeRequest {
    private SkinDto currentSkin;
    private SkinDto newSkin;
}
