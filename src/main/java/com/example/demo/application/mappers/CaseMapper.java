package com.example.demo.application.mappers;

import com.example.demo.application.dto.CaseDto;
import com.example.demo.application.dto.CaseHomeDto;
import com.example.demo.domain.entities.CaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CaseMapper {
    private final SkinMapper skinMapper;
    public CaseDto ToDto(CaseEntity caseEntity) {
        return CaseDto.builder()
                .id(caseEntity.getId())
                .price(caseEntity.getPrice())
                .skins(caseEntity.getSkins().stream()
                        .map(skinMapper::toSkinDto)
                        .collect(Collectors.toList()))
                .imageUrl(caseEntity.getImageUrl())
                .title(caseEntity.getName())
                .build();
    }

    public CaseHomeDto ToHomeDto(CaseEntity caseEntity) {
        return CaseHomeDto.builder()
                .id(caseEntity.getId())
                .price(caseEntity.getPrice())
                .imageUrl(caseEntity.getImageUrl())
                .title(caseEntity.getName())
                .build();
    }
}
