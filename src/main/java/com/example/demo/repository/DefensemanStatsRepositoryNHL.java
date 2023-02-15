package com.example.demo.repository;

import com.example.demo.model.DefensemanStatsNHL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefensemanStatsRepositoryNHL extends JpaRepository<DefensemanStatsNHL, Long> {
    List<DefensemanStatsNHL> findAllByOrderByPointsDesc();
}