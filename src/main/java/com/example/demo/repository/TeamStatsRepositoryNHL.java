package com.example.demo.repository;

import com.example.demo.model.TeamStatsNHL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatsRepositoryNHL extends JpaRepository<TeamStatsNHL, Long> {
    List<TeamStatsNHL> findAllByOrderByPowerPlayDesc();
}
