package com.example.social_network.dto;

import com.example.social_network.domain.City;
import com.example.social_network.utils.Genders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Dto для сущности User (пользователь) на страницу регистрации
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotNull
    @Length(min = 1, max = 45)
    private String name;

    @NotNull
    @Length(min = 1, max = 45)
    private String surname;

    @Positive
    @Max(125)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    @Length(max = 512)
    private String interests;

    private City city;
}