package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "defenseman_stats")
@NoArgsConstructor
public class DefensemanStats {
    @Id
    private String player;
    private Integer games;
    private Integer goals;
    private Integer assists;
    private Integer points;
    private String PPG;

    public DefensemanStats(String player, Integer games, Integer goals, Integer assists, Integer points, String PPG) {
        this.player = player;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
        this.points = points;
        this.PPG = PPG;
    }
}
