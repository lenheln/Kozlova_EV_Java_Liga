package com.example.social_network.dto;

import com.example.social_network.utils.Genders;
import lombok.Builder;
import lombok.Data;

/**
 * Dto для сущности User (пользователь) на страницу регистрации
 */
@Data
@Builder
public class UserRegisterDto {

    private String name;
    private String surname;
    private Integer age;
    private Genders gender;
    private String interests;
    private String city;
}