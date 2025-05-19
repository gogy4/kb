package com.example.demo.application.service;

import com.example.demo.application.dto.CaseBaseInfo;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.models.SkinEntity;
import com.example.demo.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CaseDropService {
    private final CaseDropChanceService caseDropChanceService;
    private final UserRepository userRepository;
    private final SkinMapper skinMapper;
    private final Random random = new Random();

    //временно будет приватным для тестов
    public SkinEntity getDroppedSkin(CaseBaseInfo caseBaseInfo, double winningChance){
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

    public SkinDto dropSkin(CaseBaseInfo caseBaseInfo, double winningChance, long userId){
        var skin = getDroppedSkin(caseBaseInfo, winningChance);
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));;
        user.addSkin(skin);
        userRepository.save(user);
        return skinMapper.toSkinDto(skin);
    }
}
