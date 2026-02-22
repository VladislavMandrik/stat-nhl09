package com.example.demo.repository;

import com.example.demo.model.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
    List<TeamStats> findAllByOrderByPowerPlayDesc();

}
