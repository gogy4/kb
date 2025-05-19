package com.example.demo.application.service;

import com.example.demo.application.dto.CaseBaseDto;
import com.example.demo.application.mappers.CaseMapper;
import com.example.demo.infrastructure.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseService {
    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;

    public List<CaseBaseDto> getAllCases() {
        return (caseRepository.findAll()).stream().map(caseMapper::ToDto).collect(Collectors.toList());
    }

    public CaseBaseDto getCaseById(Long id) {
        return caseMapper.ToDto(caseRepository.findById(id).orElse(null));
    }
}
