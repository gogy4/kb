package com.example.demo.application.service;

import com.example.demo.application.dto.CaseDto;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.mappers.CaseMapper;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.CaseRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CaseDropService {
    private final CaseDropChanceService caseDropChanceService;
    private final UserRepository userRepository;
    private final SkinMapper skinMapper;
    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;
    private final Random random = new Random();
    private final UserMapper userMapper;

    //временно будет публичным для тестов
    public SkinEntity getDroppedSkin(CaseDto caseBaseInfo, double winningChance){
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

    @Transactional
    public SkinDto dropSkin(long caseId, double winningChance, long userId){
        var caseBaseInfo = caseRepository.findById(caseId).orElse(null);
        if (caseBaseInfo == null) return null;
        var skin = getDroppedSkin(caseMapper.ToDto(caseBaseInfo), winningChance);
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));;
        user.addSkin(skin);
        user.setBalance(user.getBalance() - caseBaseInfo.getPrice());
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return skinMapper.toSkinDto(skin);
    }
}
