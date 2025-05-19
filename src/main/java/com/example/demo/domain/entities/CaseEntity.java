package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageUrl;
    private double price;
    @ManyToMany
    @JoinTable(
            name="case_skin",
            joinColumns = @JoinColumn(name="case_id"),
            inverseJoinColumns = @JoinColumn(name="skin_id")
    )
    private List<SkinEntity> skins;
}
