package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private long id;
    private String name;
    private String avatarUrl;
    private List<SkinDto> skins;
    private String tradeLink;
    private double winningChance;
    private double winningBalance;
    private double balance;
    private double lastBalanceDeposit;
}
