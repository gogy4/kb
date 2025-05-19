package com.example.demo.controller;

import com.example.demo.application.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final CaseService caseService;

    @GetMapping("/")
    public String getAllCases(Model model) {
        model.addAttribute("cases", caseService.getAllCases());
        return "home";
    }
}
