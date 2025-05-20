package com.example.demo.controller;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.UpgradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            model.addAttribute("userSkins", user == null ? null : user.getSkins());
            return "upgrade";
        }

        return "";
    }

    @PostMapping("skin-upgrade")
    public String skinUpgrade(Authentication auth, SkinDto skinDto, SkinDto newSkin, Model model) {
        var principal = auth.getPrincipal();

        if (principal instanceof UserDto user) {
            var upgrade = upgradeService.upgradeSkin(skinDto, newSkin, user.getId());
            if (upgrade == null) return "upgrade";
            model.addAttribute("upgrade", upgrade);
            return "skin-upgrade";
        }

        return "";
    }

    @GetMapping("get-available-skins-upgrade")
    public String getAvailableSkinUpgrade(Authentication auth, Model model, SkinDto skinDto) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var availableSKins = upgradeService.getAvailableSkins(skinDto);
            model.addAttribute("availableSkins", availableSKins);
            return "available-skin-upgrade";
        }

        return "";
    }

    @GetMapping("get-available-multiply-skin-upgrade")
    public String getAvailableMultiplySkinUpgrade(Authentication auth, Model model, SkinDto skinDto,
                                                  double multiplier) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var availableSKins = upgradeService.getAvailableSkins(skinDto, multiplier);
            model.addAttribute("availableSkins", availableSKins);
            return "available-skin-upgrade";
        }

        return "";
    }
}
