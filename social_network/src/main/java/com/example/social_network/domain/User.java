package com.example.social_network.domain;

import com.example.social_network.utils.Genders;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность User (пользователь)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "surname")
    private String surname;

    //TODO: добавить валиадцию что >0
    @NonNull
    @Column(name = "age")
    private Integer age;

    @NonNull
    @Column(name = "gender")
    private Genders gender;

    @Column(name = "interests")
    private String interests;

    //TODO: можно выпадающим списком. Перечисления или база данных какая-то городов
    @Column(name = "city")
    private String city;

//    private List<User> friends;
}
