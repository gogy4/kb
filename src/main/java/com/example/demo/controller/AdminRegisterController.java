package com.example.demo.controller;

import com.example.demo.application.dto.AdminDto;
import com.example.demo.application.dto.AdminLoginRequest;
import com.example.demo.application.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminRegisterController {
    private final AdminLoginService adminLoginService;

    @PostMapping("admin/register")
    public AdminDto register(@RequestBody AdminLoginRequest adminLoginRequest) {
        return adminLoginService.register(adminLoginRequest.getEmail(), adminLoginRequest.getPassword());
    }
}
