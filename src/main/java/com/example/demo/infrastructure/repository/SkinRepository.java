package com.example.demo.infrastructure.repository;

import com.example.demo.domain.models.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
}
