package com.example.demo.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadDir;

    public String store(MultipartFile file) throws IOException {
        String filename = System.currentTimeMillis() + "_" +
                StringUtils.cleanPath(file.getOriginalFilename());

        var target = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }

        var destination = target.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + filename;
    }
}
