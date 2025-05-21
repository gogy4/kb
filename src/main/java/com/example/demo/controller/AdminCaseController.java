package com.example.demo.controller;

import com.example.demo.application.dto.AddCaseRequest;
import com.example.demo.application.dto.CaseDto;
import com.example.demo.application.dto.SkinBaseDto;
import com.example.demo.application.service.AdminCaseService;
import com.example.demo.application.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminCaseController {
    private final AdminCaseService adminCaseService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Добавить новый кейс с загрузкой изображения")
    @PostMapping(value = "/add-case", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CaseDto addCase(@ModelAttribute AddCaseRequest request) throws Exception {
        var avatarUrl = fileStorageService.store(request.getAvatarFile());
        return adminCaseService.addNewCase(request.getName(), request.getPrice(), avatarUrl);
    }


    @PostMapping("add-skin-to-case")
    public String addSkinToCase(@RequestParam String caseName, @RequestParam String skinName){
        adminCaseService.addNewSkinToCase(caseName, skinName);
        return "Скин " + skinName + " добавлен в кейс" + caseName;
    }
}
