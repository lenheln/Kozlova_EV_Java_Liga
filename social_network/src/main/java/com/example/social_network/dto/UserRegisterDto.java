package com.example.social_network.dto;

import com.example.social_network.utils.Genders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для сущности User (пользователь) на страницу регистрации
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    private String name;
    private String surname;
    private Integer age;
    private Genders gender;
    private String interests;
    private String city;
}