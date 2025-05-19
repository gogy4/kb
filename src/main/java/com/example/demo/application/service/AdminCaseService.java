package com.example.demo.application.service;

import com.example.demo.application.dto.CaseBaseDto;
import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.mappers.CaseMapper;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.entities.CaseEntity;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.CaseRepository;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCaseService {
    private final CaseRepository caseRepository;
    private final SkinRepository skinRepository;
    private final CaseMapper caseMapper;
    private final SkinMapper skinMapper;

    public CaseBaseDto addNewCase(String name, int price, String avatarUrl){
        var newCase = CaseEntity.builder()
                .name(name)
                .price(price)
                .imageUrl(avatarUrl)
                .skins(new ArrayList<>())
                .build();
        caseRepository.save(newCase);
        return caseMapper.ToDto(newCase);
    }

    public void addNewSkinToCase(String caseName, String skinName){
        CaseEntity caseEntity = caseRepository.findByName(caseName);
        var caseSkins = caseEntity.getSkins();
        SkinEntity skinEntity = skinRepository.findByName(skinName);
        caseSkins.add(skinEntity);
        caseEntity.setSkins(caseSkins);
        caseRepository.save(caseEntity);
    }
}
