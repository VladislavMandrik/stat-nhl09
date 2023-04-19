package com.example.demo.repository;

import com.example.demo.model.Playerstat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlayerstatRepo extends JpaRepository<Playerstat, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE playerstat", nativeQuery = true)
    void truncateTable();
}
