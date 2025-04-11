package com.example.demo.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tradeUrl;
    private String userName;
    private double winningChance;
    private double balance;
    //Может быть и отрицательным, если не везуха
    private double winningBalance;
    @ManyToMany
    @JoinTable(
            name="user_skin",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="skin_id")
    )
    private List<Skin> skins;
}
