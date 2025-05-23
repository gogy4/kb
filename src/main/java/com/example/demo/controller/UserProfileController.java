package com.example.demo.controller;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;
    @GetMapping("/profile")
    public String profileView(Authentication auth, Model model) {
        var principal = auth.getPrincipal();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDto userDto) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userDto);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }

        if (principal instanceof UserDto user) {
            model.addAttribute("user", user);
            return "profile";
        }
        return "redirect:/login";
    }

    @PostMapping("/profile/trade-link")
    public String updateTradeLink(@RequestParam String tradeLink, Authentication auth) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            userService.saveTradeLink(user, tradeLink);
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/send-all-skins")
    @ResponseBody
    public List<SkinDto> sendAllSkins(Authentication auth) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            userService.sendAllSkins(user.getId());
            return user.getSkins();
        }

        return null;
    }

    @PostMapping("/profile/send-one-skin/{skinId}")
    @ResponseBody
    public List<SkinDto> sendOneSkin(Authentication auth, @PathVariable long skinId) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            userService.sendOneSkin(user.getId(), skinId);
            return user.getSkins();
        }

        return null;
    }
}
