package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entities.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Long> {
    CaseEntity findByName(String name);

}
