package com.example.demo.repository;

import com.example.demo.model.Teamstat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TeamstatRepo extends JpaRepository<Teamstat, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE teamstat", nativeQuery = true)
    void truncateTable();
}
