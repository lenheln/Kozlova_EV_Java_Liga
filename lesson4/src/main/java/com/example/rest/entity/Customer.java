package com.example.rest.entity;

//TODO: write comments everywhere

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Описывает сущность Customer / Клиент
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int id;
    private String name;
    private String email;
}
