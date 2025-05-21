package com.example.demo.application.service;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.mappers.UserMapper;
import com.example.demo.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Transactional
    public void saveTradeLink(UserDto userDto, String url) {
        var user = userRepository.findById(userDto.getId()).get();
        user.setTradeLink(url);
        userRepository.save(user);
    }

    public UserDto getUserById(long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElse(null));
    }

    @Transactional
    public UserDto addBalance(long userId, double amount) {
        var user = userRepository.findById(userId).get();
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );

        return updatedUserDto;
    }

    @Transactional
    public UserDto setBalance(long userId, double amount) {
        var user = userRepository.findById(userId).get();
        user.setBalance(amount);
        userRepository.save(user);
        var updatedUserDto = userMapper.toUserDto(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        updatedUserDto,
                        null,
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );

        return updatedUserDto;
    }
}
