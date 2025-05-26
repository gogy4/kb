package com.example.demo.application.service;

import com.example.demo.application.mappers.UserMapper;
import com.example.demo.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserMapper userMapper;

    //перерасчет winningchance
    public double getUserWinningChance(long userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var winningChance = adjustWinningChanceByBalance(user.getWinningBalance(), user.getWinningChance());
        user.setWinningChance(winningChance);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );

        return winningChance;
    }

    private double adjustWinningChanceByBalance(double winningBalance, double adjustedChance) {
        if (winningBalance < 0) {
            adjustedChance += (Math.abs(winningBalance) / 100) * NEGATIVE_BALANCE_BONUS;
            adjustedChance = Math.min(adjustedChance, MAXIMUM_BALANCE_BONUS);
        } else if (winningBalance > 0) {
            adjustedChance -= (winningBalance / 100) * POSITIVE_BALANCE_PENALTY;
            adjustedChance = Math.max(adjustedChance, MINIMUM_BALANCE_PENALTY);
        }

        return adjustedChance;
    }
}
