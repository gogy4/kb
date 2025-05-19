package com.example.demo.application.service;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSkinService {
    private final SkinRepository skinRepository;
    private final SkinMapper skinMapper;

    public SkinDto addNewSkin(String skinName, int price, String imageUrl){
        var entity = SkinEntity.builder()
                .name(skinName)
                .price(price)
                .imageUrl(imageUrl)
                .build();
        skinRepository.save(entity);
        return skinMapper.toSkinDto(entity);
    }
}
