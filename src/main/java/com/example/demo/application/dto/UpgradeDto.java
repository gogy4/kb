package com.example.demo.application.dto;

import com.example.demo.domain.models.Skin;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpgradeDto {
    private double rolledChance;
    private double upgradeChance;
    private boolean success;
}
