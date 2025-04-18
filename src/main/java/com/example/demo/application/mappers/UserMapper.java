package com.example.demo.application.mappers;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.dto.UserWinningChanceResponse;
import com.example.demo.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final SkinMapper skinMapper;
    public static UserWinningChanceResponse toWinningChangeResponse(User user) {
        return UserWinningChanceResponse.builder()
                .id(user.getId())
                .winningChange(user.getWinningChance())
                .winningBalance(user.getWinningBalance())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUserName())
                .avatarUrl(user.getAvatarUrl())
                .skins(user.getSkins().stream().map(skinMapper::toSkinDto).collect(Collectors.toList()))
                .build();
    }
}
