package com.example.rest.domain;

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
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String email;
}
