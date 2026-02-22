package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "teamstat")
@NoArgsConstructor
public class Teamstat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamstat;

    public Teamstat(String teamstat) {
        this.teamstat = teamstat;
    }
}
