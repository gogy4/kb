package com.example.demo.application.service;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.domain.models.Skin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UpgradeChanceService {
    private double getUpgradeChance(SkinBaseDto currentItem, SkinBaseDto targetItem, double winningChance){
        var chance = currentItem.getPrice() / targetItem.getPrice();
        if (winningChance > 0.5){
            chance *= 1 + winningChance - 0.5;
        }
        else if(winningChance < 0.5) {
            chance *= 1 - 0.5 - winningChance;
        }
        return Math.min(1, chance);
    }

    public UpgradeDto performUpgrade(SkinBaseDto currentItem, SkinBaseDto targetItem, double winningChance){
        var upgradeChance  = getUpgradeChance(currentItem, targetItem, winningChance);
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
