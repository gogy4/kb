package com.example.demo.application.service;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.application.mappers.SkinMapper;
import com.example.demo.domain.models.Skin;
import com.example.demo.infrastructure.repository.SkinRepository;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpgradeService {
    private final SkinRepository skinRepository;
    private final UpgradeChanceService upgradeChanceService;
    private final UserRepository userRepository;
    private final SkinMapper skinMapper;

    public List<Skin> getAvailableSkins(SkinBaseDto skinBaseDto) {
        return skinRepository.finAvailableSkins(skinBaseDto.getPrice());
    }

    public List<Skin> getAvailableSkins(SkinBaseDto skinBaseDto, double multiplier) {
        return skinRepository.finAvailableSkins(skinBaseDto.getPrice(), multiplier);
    }

    @Transactional
    public UpgradeDto upgradeSkin(Skin currentSkin, Skin targetSkin, long userId) {
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
