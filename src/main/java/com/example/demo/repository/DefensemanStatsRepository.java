package com.example.demo.repository;

import com.example.demo.model.DefensemanStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefensemanStatsRepository extends JpaRepository<DefensemanStats, Long> {
    List<DefensemanStats> findAllByOrderByPointsDesc();
}
