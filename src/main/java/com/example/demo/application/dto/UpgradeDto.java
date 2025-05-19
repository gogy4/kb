package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpgradeDto {
    private double rolledChance;
    private double upgradeChance;
    private boolean success;
}
