package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@Table(name = "res")
@NoArgsConstructor
public class Res {
    @Id
    private String res;

    public Res(String res) {
        this.res = res;
    }
}