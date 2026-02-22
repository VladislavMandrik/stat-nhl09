package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "logger")
@NoArgsConstructor
public class Logger {
    @Id
    private String log;

    public Logger(String log) {
        this.log = log;
    }
}
