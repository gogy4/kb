package com.example.demo.application.mappers;

import com.example.demo.application.dto.CaseBaseDto;
import com.example.demo.domain.entities.CaseEntity;
import com.example.demo.infrastructure.repository.CaseRepository;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CaseMapper {
    private final SkinMapper skinMapper;
    public CaseBaseDto ToDto(CaseEntity caseEntity) {
        return CaseBaseDto.builder()
                .id(caseEntity.getId())
                .price(caseEntity.getPrice())
                .skins(caseEntity.getSkins().stream()
                        .map(skinMapper::toSkinBase)
                        .collect(Collectors.toList()))
                .build();
    }
}
