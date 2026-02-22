package com.example.demo.repository;

import com.example.demo.model.PlayerStats;
import com.example.demo.model.Standings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingsRepository extends JpaRepository<Standings, Long> {
    List<Standings> findAllByOrderByPointsDesc();
    @Query(value = "select * from " +
//            "e " +
            "a " +
            "order by points desc", nativeQuery = true)
    List<Standings> findByGroupA();

    @Query(value = "select * from " +
//            "w " +
            "b " +
            "order by points desc", nativeQuery = true)
    List<Standings> findByGroupB();
}

