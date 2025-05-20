package com.example.demo.application.service;

import com.example.demo.application.dto.CaseDto;
import com.example.demo.application.dto.CaseHomeDto;
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

    public List<CaseHomeDto> getAllCases() {
        return (caseRepository.findAll()).stream().map(caseMapper::ToHomeDto).collect(Collectors.toList());
    }

    public CaseDto getCaseById(Long id) {
        return caseMapper.ToDto(caseRepository.findById(id).orElse(null));
    }
}
