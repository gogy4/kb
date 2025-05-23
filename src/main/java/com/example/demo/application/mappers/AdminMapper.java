package com.example.demo.application.mappers;

import com.example.demo.application.dto.AdminDto;
import com.example.demo.domain.entities.AdminEntity;
import com.example.demo.domain.entities.SkinEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminMapper {
    public AdminDto toAdminDto(AdminEntity skin) {
        return AdminDto.builder()
                .id(skin.getId())
                .email(skin.getEmail())
                .password(skin.getPassword())
                .build();
    }
}
