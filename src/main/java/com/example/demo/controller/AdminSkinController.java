package com.example.demo.controller;

import com.example.demo.application.service.AdminSkinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminSkinController {
    private final AdminSkinService adminSkinService;
    @PostMapping("add-skin")
    public String addSkin(@RequestParam String name, @RequestParam int price, @RequestParam String avatarUrl) {
        var skinDto = adminSkinService.addNewSkin(name, price, avatarUrl);
        return "Скин добавлен: " + skinDto.getId();
    }
}
