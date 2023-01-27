package com.example.demo.repository;

import com.example.demo.model.Standings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingsRepository extends JpaRepository<Standings, Long> {
}

