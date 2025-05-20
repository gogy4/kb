package com.example.demo.controller;

import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.CaseDropService;
import com.example.demo.application.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CaseController {
    private final CaseDropService caseDropService;
    private final CaseService caseService;

    @GetMapping("case/{caseId}")
    public String getCase(@PathVariable long caseId, Model model, Authentication auth){
        var caseDto = caseService.getCaseById(caseId);
        model.addAttribute("case", caseDto);
        model.addAttribute("skins", caseDto.getSkins());
        var isAuth = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isAuth);
        return "case";
    }

    @PostMapping("/open-case/{caseId}")
    @ResponseBody
    public SkinDto openCase(@PathVariable long caseId, Authentication auth) throws Exception {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var skin = caseDropService.dropSkin(caseId, user.getWinningChance(), user.getId());
            return skin;
        }
        throw new RuntimeException("Unauthorized");
    }
}
