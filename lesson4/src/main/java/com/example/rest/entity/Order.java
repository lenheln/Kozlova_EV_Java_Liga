package com.example.rest.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/*
    Описывает продукт
 */
@Data
public class Order {

    private int id;
    private String name;
    private int price;
    private int customerId;

    public Order(){    }

    public Order(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Order(int id, String name, int price, int customerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.customerId = customerId;
    }
}
