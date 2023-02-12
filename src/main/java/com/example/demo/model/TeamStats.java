package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "team_stats")
@NoArgsConstructor
public class TeamStats {
    @Id
    private String team;
    private Integer onetimerShots;
    private Integer onetimerGoals;
    private String onetimerGoalsPercentage;
    private Integer games;
    private String OTPG;
    private String OTGPG;
    private String powerPlay;
    private String penaltyKill;

    public TeamStats(String team, Integer onetimerShots, Integer onetimerGoals, String onetimerGoalsPercentage,
                     Integer games, String OTPG, String OTGPG, String powerPlay, String penaltyKill) {
        this.team = team;
        this.onetimerShots = onetimerShots;
        this.onetimerGoals = onetimerGoals;
        this.onetimerGoalsPercentage = onetimerGoalsPercentage;
        this.games = games;
        this.OTPG = OTPG;
        this.OTGPG = OTGPG;
        this.powerPlay = powerPlay;
        this.penaltyKill = penaltyKill;
    }
}
