package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddSkinRequest {
    private String name;
    private int price;

    @Schema(type = "string", format = "binary")
    private MultipartFile avatarFile;
}
