package com.example.demo.controller;

import com.example.demo.application.dto.OpenCaseResponse;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.dto.UserDto;
import com.example.demo.application.service.CaseDropService;
import com.example.demo.application.service.CaseService;
import com.example.demo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CaseController {
    private final CaseDropService caseDropService;
    private final CaseService caseService;
    private final UserService userService;

    @GetMapping("case/{caseId}")
    public String getCase(@PathVariable long caseId, Model model, Authentication auth) {
        var caseDto = caseService.getCaseById(caseId);
        model.addAttribute("case", caseDto);
        model.addAttribute("skins", caseDto.getSkins());

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDto userDto) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("needToPay", caseDto.getPrice() - userDto.getBalance());
            model.addAttribute("user", userDto);
            var canOpen = userDto.getBalance() >= caseDto.getPrice();
            model.addAttribute("canOpenCase", canOpen);
        } else {
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("canOpenCase", false);
        }

        return "case";
    }


    @PostMapping("/open-case/{caseId}")
    @ResponseBody
    public OpenCaseResponse openCase(@PathVariable long caseId, Authentication auth) throws Exception {
        var principal = auth.getPrincipal();
        if (principal instanceof UserDto user) {
            var skin = caseDropService.dropSkin(caseId, user.getId());
            var newBalance = userService.getUserById(user.getId()).getBalance();
            return OpenCaseResponse.builder().balance(newBalance).skin(skin).build();
        }
        throw new RuntimeException("Unauthorized");
    }
}
