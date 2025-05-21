package com.example.demo.controller;

import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddBalanceUserController {
    private final UserService userService;

    @PostMapping("add-balance/{userId}")
    public UserDto addBalance(@PathVariable long userId, @RequestParam int amount) {
        var user = userService.addBalance(userId, amount);
        return user;
    }

    @PostMapping("set-balance/{userId}")
    public UserDto setBalance(@PathVariable long userId, @RequestParam int amount) {
        var user = userService.setBalance(userId, amount);
        return user;
    }
}
