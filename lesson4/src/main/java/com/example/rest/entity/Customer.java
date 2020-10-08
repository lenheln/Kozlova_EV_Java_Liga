package com.example.rest.entity;

//TODO: write comments everywhere

import lombok.Data;

@Data
public class Customer {

    private int id;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
