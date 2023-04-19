package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "playerstat")
@NoArgsConstructor
public class Playerstat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerstat;

    public Playerstat(String playerstat) {
        this.playerstat = playerstat;
    }
}
