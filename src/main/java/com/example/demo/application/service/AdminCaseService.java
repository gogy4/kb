package com.example.demo.application.service;

import com.example.demo.application.dto.CaseDto;
import com.example.demo.application.mappers.CaseMapper;
import com.example.demo.domain.entities.CaseEntity;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.CaseRepository;
import com.example.demo.infrastructure.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AdminCaseService {
    private final CaseRepository caseRepository;
    private final SkinRepository skinRepository;
    private final CaseMapper caseMapper;

    public CaseDto addNewCase(String name, int price, String imageUrl){
        var newCase = CaseEntity.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .skins(new ArrayList<>())
                .build();
        caseRepository.save(newCase);
        return caseMapper.ToDto(newCase);
    }

    public void addNewSkinToCase(String caseName, String skinName){
        var caseEntity = caseRepository.findByName(caseName);
        var caseSkins = caseEntity.getSkins();
        var skinEntity = skinRepository.findByName(skinName);
        caseSkins.add(skinEntity);
        caseEntity.setSkins(caseSkins);
        caseRepository.save(caseEntity);
    }
}
