package com.example.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

/**
 * Описывает сущность Customer / Клиент
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String email;
}
