package com.example.social_network.dto;

import com.example.social_network.utils.Genders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Dto для отображения на странице пользователя
 */

//TODO чем еще может отличаться от другой дто
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {
    private String fio;
    private Integer age;
    private Genders gender;
    private String interests;
    private String city;
}