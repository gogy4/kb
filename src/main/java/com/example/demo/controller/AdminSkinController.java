package com.example.demo.controller;

import com.example.demo.application.dto.AddSkinRequest;
import com.example.demo.application.dto.SkinDto;
import com.example.demo.application.service.AdminSkinService;
import com.example.demo.application.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminSkinController {
    private final AdminSkinService adminSkinService;
    private final FileStorageService fileStorageService;

    @Operation(
            summary = "Добавить новый скин с загрузкой изображения",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = AddSkinRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Скин успешно добавлен")
            }
    )    @PostMapping(value = "/add-skin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SkinDto addSkin(@ModelAttribute AddSkinRequest request) throws Exception {
        String avatarUrl = fileStorageService.store(request.getAvatarFile());
        return adminSkinService.addNewSkin(request.getName(), request.getPrice(), avatarUrl);
    }
}
