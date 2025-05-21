package com.example.demo.application.mappers;

import com.example.demo.application.dto.UserDto;
import com.example.demo.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final SkinMapper skinMapper;

    public UserDto toUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUserName())
                .avatarUrl(user.getAvatarUrl())
                .skins(user.getSkins().stream().map(skinMapper::toSkinDto).collect(Collectors.toList()))
                .tradeLink(user.getTradeLink())
                .winningChance(user.getWinningChance())
                .winningBalance(user.getWinningBalance())
                .balance(user.getBalance())
                .build();
    }
}
