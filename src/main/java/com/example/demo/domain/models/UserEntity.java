package com.example.demo.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    private long id;
    private String tradeUrl;
    private String userName;
    private String avatarUrl;
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

    private List<SkinEntity> skins;

    public UserEntity(long id){
        this.id = id;
        winningBalance = 0;
        winningChance = 50;
        balance = 0;
        skins = new ArrayList<>();
    }

    public void addSkin(SkinEntity skin) {
        skins.add(skin);
    }

    public void removeSkin(SkinEntity skin) {
        skins.remove(skin);
    }
}
