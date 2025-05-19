package com.example.demo.application.service;

import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.domain.models.UserEntity;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProcessSteamLoginService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    //нужно возваращать пользователя из бд или создавать нового с steamId.
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
            var steamIdStr = identity.substring(0, identity.length() - 1)
                    .substring(identity.lastIndexOf("/") + 1);
            var steamId = Long.parseLong(steamIdStr);
            var user = userRepository.findById(steamId).orElse(null);
            session.setAttribute("steamId", steamId);
            if (user != null){
                return userMapper.toUserDto(user);
            }
            else{
                var newUser = new UserEntity(steamId);
                userRepository.save(newUser);
                return userMapper.toUserDto(newUser);
            }
        }

        return null;
    }
}
