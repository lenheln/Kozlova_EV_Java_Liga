package com.example.rest.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Описывает сущность Order / Продукт
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private String name;
    private int price;
    private int customerId;

    public Order(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
