package com.example.demo.controller;

import com.example.demo.application.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class UserProfileController {
    @GetMapping("/profile")
    public String profileView(@AuthenticationPrincipal UserDto user, Model model) {
        if (user == null || user.getId() == -1) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }
}
