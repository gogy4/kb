package com.example.demo.controller;

import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.service.AdminCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCaseController {
    private final AdminCaseService adminCaseService;

    @PostMapping("add-case")
    public String addCase(@RequestParam String name, @RequestParam int price, @RequestParam String avatarUrl) {
        var caseDto = adminCaseService.addNewCase(name, price, avatarUrl);
        return "Кейс добавлен: " + caseDto.getId();
    }

    @PostMapping("add-skin-to-case")
    public String addSkinToCase(@RequestParam String caseName, @RequestParam String skinName){
        adminCaseService.addNewSkinToCase(caseName, skinName);
        return "Скин " + skinName + " добавлен в кейс" + caseName;
    }
}
