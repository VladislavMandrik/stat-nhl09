package com.example.demo.repository;

import com.example.demo.model.PlayerStats;
import com.example.demo.model.PlayerStatsNHL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepositoryNHL extends JpaRepository<PlayerStatsNHL, Long> {
    List<PlayerStatsNHL> findPlayerStatsByPlusMinusIs(String s);
    List<PlayerStatsNHL> findAllByOrderByPointsDesc();
}