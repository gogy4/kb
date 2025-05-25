package com.example.demo.controller;

import com.example.demo.application.dto.SellSkinResponse;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return userService.sendAllSkins(user.getId()).getSkins();
        }

        return null;
    }

    @PostMapping("/profile/send-one-skin/{skinId}")
    @ResponseBody
    public List<SkinDto> sendOneSkin(Authentication auth, @PathVariable long skinId) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            return userService.sendOneSkin(user.getId(), skinId).getSkins();
        }

        return null;
    }

    @PostMapping("/profile/sell-all-skins")
    @ResponseBody
    public SellSkinResponse  sellAllSkins(Authentication auth) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var updatedUser = userService.sellAllSkins(user.getId());
            return SellSkinResponse.builder()
                    .user(updatedUser)
                    .balance(updatedUser.getBalance())
                    .skins(updatedUser.getSkins())
                    .build();        }

        return null;
    }

    @PostMapping("/profile/sell-one-skin/{skinId}")
    @ResponseBody
    public SellSkinResponse sellOneSkin(Authentication auth, @PathVariable long skinId) {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var updatedUser = userService.sellOneSkin(user.getId(), skinId);
            return SellSkinResponse.builder()
                    .user(updatedUser)
                    .balance(updatedUser.getBalance())
                    .skins(updatedUser.getSkins())
                    .build();
        }

        return null;
    }
}
