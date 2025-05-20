package com.example.demo.controller;

import com.example.demo.application.dto.SkinDto;
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
    @ResponseBody
    public List<SkinDto> getAvailableSkinUpgrade(Authentication auth, SkinDto skinDto) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            return upgradeService.getAvailableSkins(skinDto);
        }
        return List.of();
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
