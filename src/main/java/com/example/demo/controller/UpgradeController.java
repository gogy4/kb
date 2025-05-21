package com.example.demo.controller;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.SkinUpgradeRequest;
import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.UpgradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UpgradeController {
    private final UpgradeService upgradeService;

    @GetMapping("upgrades")
    public String showUpgradePage(Authentication auth, Model model) {
        if (auth == null) {
            model.addAttribute("userSkins", null);
            return "upgrade";
        }
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDto userDto) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userDto);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            model.addAttribute("userSkins", user == null ? null : user.getSkins());

            return "upgrade";
        }

        return "";
    }

    @GetMapping("/get-user-skins")
    @ResponseBody
    public List<SkinDto> getUserSkins(Authentication auth) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            return user.getSkins();
        }
        return List.of();
    }

    @PostMapping("skin-upgrade")
    @ResponseBody
    public UpgradeDto skinUpgrade(Authentication auth, @RequestBody SkinUpgradeRequest request) {
        var principal = auth.getPrincipal();

        if (principal instanceof UserDto user) {
            var upgrade = upgradeService.upgradeSkin(request.getCurrentSkin(), request.getNewSkin(), user.getId());
            return upgrade != null ? upgrade : UpgradeDto.builder()
                    .success(false)
                    .rolledChance(0)
                    .upgradeChance(0)
                    .build();
        }

        return UpgradeDto.builder()
                .success(false)
                .rolledChance(0)
                .upgradeChance(0)
                .build();
    }

    @GetMapping("get-available-skins-upgrade")
    @ResponseBody
    public List<SkinDto> getAvailableSkinUpgrade(Authentication auth, SkinDto skinDto) {
        var skins = upgradeService.getAvailableSkins(skinDto);
        return skins;
    }

    @GetMapping("get-available-multiply-skin-upgrade")
    @ResponseBody
    public List<SkinDto> getAvailableMultiplySkinUpgrade(Authentication auth, SkinDto skinDto,
                                                         @RequestParam double multiplier) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            return  upgradeService.getAvailableSkins(skinDto, multiplier);
        }
        return List.of();
    }
}
