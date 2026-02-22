package com.example.demo.repository;

import com.example.demo.model.TransfersStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransfersRepository extends JpaRepository<TransfersStats, Long> {
    List<TransfersStats> findAllByOrderByPlayerAsc();
}
