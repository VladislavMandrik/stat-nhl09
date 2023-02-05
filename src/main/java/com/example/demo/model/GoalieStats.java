package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "goalie_stats")
@NoArgsConstructor
public class GoalieStats {
    @Id
    private String player;
    private Integer games;
    private Integer shotsAgainst;
    private Integer goalsAgainst;
    private String savePercentage;
    private String GAA;
    private Double TOI;

    public GoalieStats(String player, Integer games, Integer shotsAgainst, Integer goalsAgainst,
                       String savePercentage, String GAA, Double TOI) {
        this.player = player;
        this.games = games;
        this.shotsAgainst = shotsAgainst;
        this.goalsAgainst = goalsAgainst;
        this.savePercentage = savePercentage;
        this.GAA = GAA;
        this.TOI = TOI;
    }
}

