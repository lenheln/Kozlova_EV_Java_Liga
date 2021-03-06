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
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Dto для сущности User (пользователь) на страницу регистрации
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotNull
    @Length(min = 1, max = 45)
    private String name;

    @NotNull
    @Length(min = 1, max = 45)
    private String surname;

    @Past
    private LocalDate dateOfBDay;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    private City city;
}