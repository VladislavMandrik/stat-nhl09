package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "defenseman_statsbrhl")
@NoArgsConstructor
public class DefensemanStats {
    @Id
    private String player;
    private Integer games;
    private Integer goals;
    private Integer assists;
    private Integer points;
    private String PPG;
    private Integer plusMinus;

    public DefensemanStats(String player, Integer games, Integer goals, Integer assists, Integer points,
                           String PPG, Integer plusMinus) {
        this.player = player;
        this.games = games;
        this.goals = goals;
        this.assists = assists;
        this.points = points;
        this.PPG = PPG;
        this.plusMinus = plusMinus;
    }
}
