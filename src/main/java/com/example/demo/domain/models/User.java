package com.example.demo.domain.models;

import jakarta.persistence.*;
import lombok.*;

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
    private double winningChange;
    private double balance;
    //Может быть и отрицательным, если не везуха
    private double winningBalance;
}
