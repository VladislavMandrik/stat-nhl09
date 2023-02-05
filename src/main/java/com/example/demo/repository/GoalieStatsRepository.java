package com.example.demo.repository;

import com.example.demo.model.GoalieStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalieStatsRepository extends JpaRepository<GoalieStats, Long> {
    List<GoalieStats> findGoalieStatsByGamesBetween(int from, int to);
}
