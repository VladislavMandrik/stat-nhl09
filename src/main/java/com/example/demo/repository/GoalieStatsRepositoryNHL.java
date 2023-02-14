package com.example.demo.repository;

import com.example.demo.model.GoalieStatsNHL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalieStatsRepositoryNHL extends JpaRepository<GoalieStatsNHL, Long> {
    List<GoalieStatsNHL> findGoalieStatsByGamesBetween(int from, int to);
    List<GoalieStatsNHL> findAllByOrderBySavePercentageDesc();
}