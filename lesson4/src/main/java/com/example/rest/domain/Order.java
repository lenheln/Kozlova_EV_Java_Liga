package com.example.rest.domain;
import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Описывает сущность Order / Продукт
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {

    @Id
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Integer price;
    @NonNull
    private Integer customerId;

//    public Order(String name, int price) {
//        this.name = name;
//        this.price = price;
//    }
}
