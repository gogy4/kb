package com.example.demo.application.service;

import com.example.demo.application.dto.AdminDto;
import com.example.demo.application.mappers.AdminMapper;
import com.example.demo.domain.entities.AdminEntity;
import com.example.demo.infrastructure.repository.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminLoginService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public Boolean login(String email, String password, HttpServletRequest request) {
        var admin = adminRepository.findByEmail(email);
        if (admin == null) {
            return false;
        }

        if (!BCrypt.checkpw(password, admin.getPassword())) {
            return false;
        }

        var adminDto = adminMapper.toAdminDto(admin);
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        var session = request.getSession();

        session.setAttribute("admin", adminDto);
        var auth = new UsernamePasswordAuthenticationToken(adminDto, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT", new SecurityContextImpl(auth));

        return true;
    }


    public AdminDto register(String email, String password) {
        var admin = AdminEntity.builder()
                .email(email)
                .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                .build();
        adminRepository.save(admin);
        return adminMapper.toAdminDto(admin);
    }
}
