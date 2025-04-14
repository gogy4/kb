package com.example.demo.application.mappers;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserWinningChanceResponse;
import com.example.demo.domain.models.Skin;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SkinMapper {
    private final SkinRepository skinRepository;
    public SkinBaseDto toSkinBase(Skin skin) {
        return SkinBaseDto.builder()
                .id(skin.getId())
                .price(skin.getPrice())
                .build();
    }

    public SkinDto toSkinDto(Skin skin) {
        return SkinDto.builder()
                .id(skin.getId())
                .name(skin.getName())
                .price(skin.getPrice())
                .imageUrl(skin.getImageUrl())
                .build();
    }

    public Skin toSkin(SkinBaseDto skinBaseDto) {
        return skinRepository.getById(skinBaseDto.getId());
    }
}
