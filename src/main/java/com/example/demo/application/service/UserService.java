package com.example.demo.application.service;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.domain.entities.UserEntity;
import com.example.demo.infrastructure.repository.SkinRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SkinRepository skinRepository;
    private final UserChanceService userChanceService;
    private final SkinMapper skinMapper;

    @Transactional
    public void saveTradeLink(UserDto userDto, String url) {
        var user = userRepository.findById(userDto.getId()).get();
        user.setTradeLink(url);
        userRepository.save(user);
    }

    public UserDto getUserById(long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElse(null));
    }

    @Transactional
    public UserDto addBalance(long userId, double amount) {
        var user = userRepository.findById(userId).get();
        user.setBalance(user.getBalance() + amount);
        user.setLastBalanceDeposit(amount);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );

        return updatedUserDto;
    }

    @Transactional
    public UserDto sellAllSkins(long userId) {
        var user = userRepository.findById(userId).get();
        var skinPrices = user.getSkins()
                .stream()
                .mapToDouble(SkinEntity::getPrice)
                .sum();
        user.setSkins(new ArrayList<>());
        user.setBalance(user.getBalance() + skinPrices);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return updatedUserDto;
    }

    @Transactional
    public UserDto sellOneSkin(long userId, long skinId) {
        var user = userRepository.findById(userId).get();
        var skin = skinRepository.findById(skinId).get();
        user.removeSkin(skin);
        user.setBalance(user.getBalance() + skin.getPrice());
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return updatedUserDto;
    }

    @Transactional
    public UserDto setBalance(long userId, double amount) {
        var user = userRepository.findById(userId).get();
        user.setBalance(amount);
        user.setLastBalanceDeposit(amount);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );

        return updatedUserDto;
    }

    //расчет ближе к казино, будем считать от последнего депозита, не смотря на возможно большие минуса пользователя.
    private void recalculateWinningBalance(UserEntity userEntity) {
        var paybackBalance = userEntity.getSkins().stream()
                .mapToDouble(SkinEntity::getPrice)
                .sum() - userEntity.getLastBalanceDeposit();
        var winningBalance = userEntity.getWinningBalance()+paybackBalance;
        var chance = userChanceService.getUserWinningChance(userEntity.getId());
        userEntity.setWinningChance(Math.max(0.4, chance-0.1));
        userEntity.setWinningBalance(winningBalance);
    }

    @Transactional
    public UserDto sendAllSkins(long userId){
        var user = userRepository.findById(userId).get();
        recalculateWinningBalance(user);
        user.setSkins(new ArrayList<>());
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return updatedUserDto;
    }

    @Transactional
    public UserDto sendOneSkin(long userId, long skinId) {
        var user = userRepository.findById(userId).get();
        var skin = skinRepository.findById(skinId).get();
        recalculateWinningBalance(user);
        user.removeSkin(skin);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return updatedUserDto;
    }
}
