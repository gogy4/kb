package com.example.demo.application.service;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveTradeLink(UserDto userDto, String url) {
        var user = userRepository.findById(userDto.getId()).get();
        user.setTradeLink(url);
        userRepository.save(user);
    }
}
