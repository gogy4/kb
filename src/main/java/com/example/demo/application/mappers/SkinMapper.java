package com.example.demo.application.mappers;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UserWinningChanceResponse;
import com.example.demo.domain.models.Skin;
import com.example.demo.domain.models.User;

public class SkinMapper {
    public static SkinBaseDto toSkinBase(Skin skin) {
        return SkinBaseDto.builder()
                .id(skin.getId())
                .price(skin.getPrice())
                .build();
    }
}
