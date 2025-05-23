package com.example.demo.application.service;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UpgradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UpgradeChanceService {
    private final UserChanceService userChanceService;
    private double getUpgradeChance(SkinDto currentItem, SkinDto targetItem, long userId){
        var winningChance = userChanceService.getUserWinningChance(userId);
        var chance = currentItem.getPrice() / targetItem.getPrice();
        if (winningChance > 0.5){
            chance *= 1 + winningChance - 0.5;
        }
        else if(winningChance < 0.5) {
            chance *= 1 - 0.5 - winningChance;
        }
        return Math.min(1, chance);
    }

    public UpgradeDto performUpgrade(SkinDto currentItem, SkinDto targetItem, long userId){
        var upgradeChance  = getUpgradeChance(currentItem, targetItem, userId);
        var rolledChance = ThreadLocalRandom.current().nextDouble();
        var success = rolledChance <= upgradeChance;
        var delta = ThreadLocalRandom.current().nextDouble(0.02, 0.2);
        if (!success){
            var mode = ThreadLocalRandom.current().nextInt(0, 2);
            rolledChance = mode == 1 ? Math.min(1, upgradeChance + delta)
                    : Math.min(1, 0.85 + delta);
        }

        return UpgradeDto.builder()
                .success(success)
                .rolledChance(rolledChance)
                .upgradeChance(upgradeChance)
                .build();
    }
}
