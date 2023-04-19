package com.example.demo.repository;

import com.example.demo.model.Res;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResRepository extends JpaRepository<Res, Long> {
    List<Res> findAllByRes(String s);
}
