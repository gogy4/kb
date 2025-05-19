package com.example.demo.controller;

import com.example.demo.application.dto.UpgradeDto;
import com.example.demo.application.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpgradeController {

    @GetMapping("upgrade")
    public String upgrade(@AuthenticationPrincipal UserDto user, Model model) {
        if (user == null || user.getId() == -1) {
            return "redirect:/login";
        }

        model.addAttribute("userSkins", user.getSkins());
        return "upgrade";
    }
}
