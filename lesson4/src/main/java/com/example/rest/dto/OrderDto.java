package com.example.rest.dto;
import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Описывает сущность OrderDto / Продукт
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderDto {

    @Id
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Integer price;
    @NonNull
    private Integer customerId;
}
