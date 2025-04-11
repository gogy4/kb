package com.example.demo.application.mappers;

import com.example.demo.application.dto.UserWinningChanceResponse;
import com.example.demo.domain.models.User;

public class UserMapper {
    public static UserWinningChanceResponse toWinningChangeResponse(User user) {
        return UserWinningChanceResponse.builder()
                .id(user.getId())
                .winningChange(user.getWinningChance())
                .winningBalance(user.getWinningBalance())
                .build();
    }
}
