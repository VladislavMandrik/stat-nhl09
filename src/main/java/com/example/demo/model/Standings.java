package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "standings")
@NoArgsConstructor
public class Standings {
    @Id
    private String team;
    private Integer games;
    private Integer wins;
    private Integer loses;
    private Integer losesOT;
    private Integer goalsScored;
    private Integer missingGoals;
    private Integer points;

    public Standings(String team, Integer games, Integer wins, Integer loses, Integer losesOT, Integer goalsScored,
                     Integer missingGoals, Integer points) {
        this.team = team;
        this.games = games;
        this.wins = wins;
        this.loses = loses;
        this.losesOT = losesOT;
        this.goalsScored = goalsScored;
        this.missingGoals = missingGoals;
        this.points = points;
    }
}
