package com.example.demo.controller;

import com.example.demo.application.dto.AdminLoginRequest;
import com.example.demo.application.service.AdminLoginService;
import com.example.demo.infrastructure.repository.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminLoginController {
    private final AdminLoginService adminLoginService;
    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute AdminLoginRequest adminLoginRequest, HttpServletRequest request) {
        var email = adminLoginRequest.getEmail();
        var password = adminLoginRequest.getPassword();
        if(!adminLoginService.login(email, password, request)) {
            return "redirect:/admin/login?error";
        }

        return "redirect:/";
    }
}
