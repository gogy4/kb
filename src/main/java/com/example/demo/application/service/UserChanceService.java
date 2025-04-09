package com.example.demo.application.service;

import com.example.demo.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChanceService {

    private static final double BASE_CHANCE = 0.5;
    private static final double NEGATIVE_BALANCE_BONUS = 0.00125; // per 100₽
    private static final double POSITIVE_BALANCE_PENALTY = 0.003; // per 100₽
    private static final double MINIMUM_BALANCE_PENALTY = 0.35;
    private static final double MAXIMUM_BALANCE_BONUS = 0.75;

    private final UserRepository userRepository;

    public double getUserWinningChance(long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return adjustWinningChanceByBalance(user.getWinningBalance());
    }

    //НЕ ЗАБЫТЬ СДЕЛАТЬ УЧЕТ ПРОЦЕНТА ПОСЛЕ ВЫВОДА!
    private double adjustWinningChanceByBalance(double winningBalance) {
        double adjustedChance = BASE_CHANCE;

        if (winningBalance < 0 && adjustedChance <= MINIMUM_BALANCE_PENALTY){
            adjustedChance += (winningBalance / 100) * NEGATIVE_BALANCE_BONUS;
        } else if (adjustedChance >= MAXIMUM_BALANCE_BONUS){
            adjustedChance -= (winningBalance / 100) * POSITIVE_BALANCE_PENALTY;
        }

        return adjustedChance;
    }
}
