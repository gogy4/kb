package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByEmail(String email);
}
