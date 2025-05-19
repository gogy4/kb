package com.example.demo.controller;

import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaseController {
    private final CaseService caseService;

    @GetMapping("get-case/{caseId}")
    public String getCase(@PathVariable long caseId, @AuthenticationPrincipal UserDto user, Model model){
        if (user == null || user.getId() == -1) {
            return "redirect:/login";
        }

        var caseDto = caseService.getCaseById(caseId);
        model.addAttribute("case", caseDto);
        model.addAttribute("skins", caseDto.getSkins());
        return "case";
    }
}
