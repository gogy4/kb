package com.example.demo.application.service;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.infrastructure.repository.SkinRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpgradeService {
    private final SkinRepository skinRepository;
    private final UpgradeChanceService upgradeChanceService;
    private final UserRepository userRepository;
    private final SkinMapper skinMapper;
    private final UserMapper userMapper;

    public List<SkinDto> getAvailableSkins(SkinDto skinBaseDto) {
        return skinRepository.findAvailableSkins(skinBaseDto.getPrice()).stream()
                .map(skinMapper::toSkinDto)
                .collect(Collectors.toList());
    }

    public List<SkinDto> getAvailableSkins(SkinDto skinBaseDto, double multiplier) {
        if (multiplier < 1.3) {
            throw new IllegalArgumentException("Multiplier must be at least 1.3x");
        }
        return skinRepository.findAvailableSkins(skinBaseDto.getPrice(), multiplier).stream()
                .map(skinMapper::toSkinDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UpgradeDto upgradeSkin(SkinDto currentSkin, SkinDto targetSkin, long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var upgradeDto = upgradeChanceService.performUpgrade(currentSkin, targetSkin, user.getId());
        if (!user.getSkins().contains(skinMapper.toSkin(currentSkin))) return null;
        user.removeSkin(skinMapper.toSkin(currentSkin));
        if (upgradeDto.isSuccess()) {
            user.addSkin(skinMapper.toSkin(targetSkin));
        }
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        return upgradeDto;
    }
}
