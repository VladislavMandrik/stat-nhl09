package com.example.demo.repository;

import com.example.demo.model.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    List<PlayerStats> findPlayerStatsByPlusMinusIs(String s);
    List<PlayerStats> findAllByOrderByPointsDesc();
}
