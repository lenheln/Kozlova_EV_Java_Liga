package com.example.rest.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

/**
 * Описывает сущность Order / Продукт
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int price;
    @NonNull
    private int customerId;

    public Order(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
