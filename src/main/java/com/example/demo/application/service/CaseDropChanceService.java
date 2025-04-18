package com.example.demo.application.service;

import com.example.demo.application.dto.CaseBaseInfo;
import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.models.Skin;
import com.example.demo.infrastructure.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CaseDropChanceService {
    private final SkinMapper skinMapper;
    public Map<Skin, Double> calculateDropChance(CaseBaseInfo caseBaseInfo, double winningChance) {
        var adjustedWeights = new HashMap<Skin, Double>();
        var skins = caseBaseInfo.getSkins();
        var casePrice = caseBaseInfo.getPrice();

        for (var skin : skins) {
            var skinPrice = skin.getPrice();
            var baseWeight = casePrice / skinPrice;
            double modifier;

            if (winningChance == 0.5) {
                modifier = 1.0;
            } else {
                var delta = Math.abs(0.5 - winningChance);
                var isExpensive = skinPrice >= casePrice;

                if (winningChance > 0.5) {
                    modifier = isExpensive ? (1 + delta) : (1 - delta);
                } else {
                    modifier = isExpensive ? (1 - delta) : (1 + delta);
                }
            }

            var adjustedWeight = baseWeight * modifier;
            adjustedWeights.put(skinMapper.toSkin(skin), adjustedWeight);
        }

        var totalAdjustedWeight = adjustedWeights.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        var chances = new HashMap<Skin, Double>();
        for (var entry : adjustedWeights.entrySet()) {
            chances.put(entry.getKey(), entry.getValue() / totalAdjustedWeight);
        }

        return chances;
    }
}
