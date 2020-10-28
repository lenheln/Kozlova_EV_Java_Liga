package com.example.social_network.dto;

import com.example.social_network.utils.Genders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/*
    Dto для отображения на странице пользователя
 */

//TODO чем еще может отличаться от другой дто
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {

    @NotNull
    @Length(max = 100)
    private String fio;

    @NotNull
    @Positive
    @Max(125)
    private Integer age;

    @NotNull
    private Genders gender;

    @Length(max = 512)
    private String interests;

    @Length(max = 45)
    private String city;
}