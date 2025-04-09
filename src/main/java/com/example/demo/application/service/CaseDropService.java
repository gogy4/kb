package com.example.demo.application.service;

import com.example.demo.application.dto.CaseBaseInfo;
import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.infrastructure.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CaseDropService {
    private final CaseDropChanceService caseDropChanceService;
    private final Random random = new Random();

    public SkinBaseDto dropSkin(CaseBaseInfo caseBaseInfo, double winningChance){
        var dropChances = caseDropChanceService.calculateDropChance(caseBaseInfo, winningChance);
        var roll = random.nextDouble();
        var cumulativeChance = 0.0;
        for (var entry: dropChances.entrySet()){
            cumulativeChance += entry.getValue();
            if (roll <= cumulativeChance){
                return entry.getKey();
            }
        }

        return dropChances
                .keySet()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
