package com.example.demo.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.openid4java.consumer.ConsumerManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final String STEAM_OPENiD = "https://steamcommunity.com/openid";
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        var manager = new ConsumerManager();
        var discoveries = manager.discover(STEAM_OPENiD);
        var discovered = manager.associate(discoveries);
        request.getSession().setAttribute("openid-disc", discovered);
        var returnUrl = "http://localhost:8080/login/return";
        var authReq = manager.authenticate(discovered, returnUrl);

        response.sendRedirect(authReq.getDestinationUrl(true));
    }
}
