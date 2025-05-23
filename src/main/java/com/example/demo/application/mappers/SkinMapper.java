package com.example.demo.application.mappers;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SkinMapper {
    private final SkinRepository skinRepository;
    public SkinDto toSkinDto(SkinEntity skin) {
        return SkinDto.builder()
                .id(skin.getId())
                .name(skin.getName())
                .price(skin.getPrice())
                .imageUrl(skin.getImageUrl())
                .build();
    }
    public SkinEntity toSkin(SkinDto skinBaseDto) {
        return skinRepository.getById(skinBaseDto.getId());
    }
}
