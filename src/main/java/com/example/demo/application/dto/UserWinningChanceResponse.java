package com.example.demo.application.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserWinningChanceResponse {
    private long id;
    private double winningChange;
    private double winningBalance;
}
