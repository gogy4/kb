package com.example.demo.application.mappers;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.domain.models.SkinEntity;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SkinMapper {
    private final SkinRepository skinRepository;
    public SkinBaseDto toSkinBase(SkinEntity skin) {
        return SkinBaseDto.builder()
                .id(skin.getId())
                .price(skin.getPrice())
                .build();
    }

    public SkinDto toSkinDto(SkinEntity skin) {
        return SkinDto.builder()
                .id(skin.getId())
                .name(skin.getName())
                .price(skin.getPrice())
                .imageUrl(skin.getImageUrl())
                .build();
    }

    public SkinEntity toSkin(SkinBaseDto skinBaseDto) {
        return skinRepository.getById(skinBaseDto.getId());
    }
}
