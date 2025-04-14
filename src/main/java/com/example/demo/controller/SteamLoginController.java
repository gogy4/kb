package com.example.demo.controller;

import com.example.demo.application.service.LoginService;
import com.example.demo.application.service.ProcessSteamService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SteamLoginController {
    private final LoginService loginService;
    private final ProcessSteamService processSteamService;

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        loginService.login(request, response);
    }

    @GetMapping("login/return")
    public String processSteamReturn(HttpServletRequest request) throws Exception{
        return processSteamService.processSteamLogin(request);
    }
}
