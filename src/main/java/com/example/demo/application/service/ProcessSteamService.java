package com.example.demo.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.springframework.stereotype.Service;


@Service
public class ProcessSteamService {
    //нужно возваращать пользователя из бд или создавать нового с steamId.
    public String processSteamLogin(HttpServletRequest request) throws Exception{
        var session = request.getSession();
        var discovered = (DiscoveryInformation) session.getAttribute("openid-disc");
        var manager = new ConsumerManager();
        var paramList = new ParameterList(request.getParameterMap());

        var receivingUrl = "http://localhost:8080" + request.getRequestURI() + request.getQueryString() != null ?
                "?" + request.getQueryString() : "";

        var verification = manager.verify(receivingUrl, paramList, discovered);
        var verified = verification.getVerifiedId();
        if (verified != null){
            var identity = verified.getIdentifier();
            var steamId = identity.substring(identity.indexOf("/") + 1);
            session.setAttribute("steamId", steamId);
            return "redirect:/";
        }

        return "redirect:/login?error";
    }
}
