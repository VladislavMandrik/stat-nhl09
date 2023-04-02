package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "goalie_statsNHL")
@NoArgsConstructor
public class GoalieStatsNHL {
    @Id
    private String player;
    private Integer games;
    private Integer shotsAgainst;
    private Integer goalsAgainst;
    private String savePercentage;
    private String GAA;
    private Double TOI;
    private Integer assists;

    public GoalieStatsNHL(String player, Integer games, Integer shotsAgainst, Integer goalsAgainst,
                       String savePercentage, String GAA, Double TOI, Integer assists) {
        this.player = player;
        this.games = games;
        this.shotsAgainst = shotsAgainst;
        this.goalsAgainst = goalsAgainst;
        this.savePercentage = savePercentage;
        this.GAA = GAA;
        this.TOI = TOI;
        this.assists = assists;
    }
}
