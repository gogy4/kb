package com.example.demo.application.service;

import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.domain.entities.UserEntity;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProcessSteamLoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Value("${steam.api.key}")
    private String steamApiKey;

    public UserDto processSteamLogin(HttpServletRequest request) throws Exception{
        var session = request.getSession();
        var discovered = (DiscoveryInformation) session.getAttribute("openid-disc");
        var manager = new ConsumerManager();
        var paramList = new ParameterList(request.getParameterMap());

        var receivingUrl = "http://localhost:8080" + request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");


        var verification = manager.verify(receivingUrl, paramList, discovered);
        var verified = verification.getVerifiedId();
        if (verified != null){
            var identity = verified.getIdentifier();
            var steamIdStr = identity.substring(identity.lastIndexOf("/") + 1);
            var steamId = Long.parseLong(steamIdStr);
            var user = userRepository.findById(steamId).orElse(null);
            session.setAttribute("steamId", steamId);
            UserEntity finalUser;
            if (user != null) {
                finalUser = user;
            } else {
                finalUser = new UserEntity(steamId);
                enrichUserFromSteam(finalUser);
                userRepository.save(finalUser);
            }
            var userDto = userMapper.toUserDto(finalUser);
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER")); // или другие роли
            var auth = new UsernamePasswordAuthenticationToken(userDto, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            session.setAttribute("SPRING_SECURITY_CONTEXT", new SecurityContextImpl(auth));
            return userDto;
        }

        return null;
    }

    //методы для получения никнейма и аватарки пользователя из стима с помощью апи

    private String getSteamProfileJson(long steamId) {
        var url = UriComponentsBuilder.fromHttpUrl("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/")
                .queryParam("key", steamApiKey)
                .queryParam("steamids", steamId)
                .toUriString();

        var restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    private void enrichUserFromSteam(UserEntity user) {
        try {
            var json = getSteamProfileJson(user.getId());
            var obj = new JSONObject(json)
                    .getJSONObject("response")
                    .getJSONArray("players")
                    .getJSONObject(0);

            user.setUserName(obj.getString("personaname"));
            user.setAvatarUrl(obj.getString("avatarfull"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}