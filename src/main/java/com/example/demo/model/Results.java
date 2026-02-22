package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "results")
@NoArgsConstructor
public class Results {
    @Id
    private String firstTeam;
    private String firstGoals;
    private String total;
    private String secondGoals;
    private String secondTeam;

    public Results(String firstTeam, String firstGoals, String total, String secondGoals, String secondTeam) {
        this.firstTeam = firstTeam;
        this.firstGoals = firstGoals;
        this.total = total;
        this.secondGoals = secondGoals;
        this.secondTeam = secondTeam;
    }
}
