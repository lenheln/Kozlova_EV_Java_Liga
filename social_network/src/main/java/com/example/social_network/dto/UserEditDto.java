package com.example.social_network.dto;

import com.example.social_network.utils.Genders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

/**
 * Dto для сущности User (пользователь) для обновления страницы
 * Поля не проверяются на NotNull, так как обновлять можно не все поля
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {
    @Length(max = 45)
    private String name;

    @Length(max = 45)
    private String surname;

    @Positive
    @Max(125)
    private Integer age;

    private Genders gender;

    @Length(max = 512)
    private String interests;

    @Length(max = 45)
    private String city;
}