package com.example.demo.repository;

import com.example.demo.model.DefensemanStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefensemanStatsRepository extends JpaRepository<DefensemanStats, Long> {
}
