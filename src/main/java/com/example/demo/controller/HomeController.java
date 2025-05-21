package com.example.demo.controller;

import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CaseService caseService;

    @GetMapping("/")
    public String getAllCases(Authentication auth, Model model) {
        model.addAttribute("cases", caseService.getAllCases());
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDto userDto) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userDto);
        }
        else {
            model.addAttribute("isAuthenticated", false);
        }
        return "home";
    }
}
