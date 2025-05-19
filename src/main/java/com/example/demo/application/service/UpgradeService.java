package com.example.demo.application.service;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.entities.SkinEntity;
import com.example.demo.infrastructure.repository.SkinRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpgradeService {
    private final SkinRepository skinRepository;
    private final UpgradeChanceService upgradeChanceService;
    private final UserRepository userRepository;
    private final SkinMapper skinMapper;

    public List<SkinEntity> getAvailableSkins(SkinBaseDto skinBaseDto) {
        return skinRepository.finAvailableSkins(skinBaseDto.getPrice());
    }

    public List<SkinEntity> getAvailableSkins(SkinBaseDto skinBaseDto, double multiplier) {
        return skinRepository.finAvailableSkins(skinBaseDto.getPrice(), multiplier);
    }

    @Transactional
    public UpgradeDto upgradeSkin(SkinEntity currentSkin, SkinEntity targetSkin, long userId) {
        var currentSkinBase = skinMapper.toSkinBase(currentSkin);
        var targetSkinBase = skinMapper.toSkinBase(targetSkin);
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var upgradeDto = upgradeChanceService.performUpgrade(currentSkinBase, targetSkinBase, user.getWinningChance());
        user.removeSkin(currentSkin);
        if (upgradeDto.isSuccess()) {
            user.addSkin(targetSkin);
        }
        userRepository.save(user);
        return upgradeDto;
    }
}
