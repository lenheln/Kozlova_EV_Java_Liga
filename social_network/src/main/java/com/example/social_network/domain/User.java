package com.example.social_network.domain;

import com.example.social_network.utils.Genders;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность User (пользователь)
 */

@Builder
@Getter
@Setter
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

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="friendship",
            joinColumns={@JoinColumn(name="idUser")},
            inverseJoinColumns={@JoinColumn(name="idFriend")})
    private Set<User> myFriends = new HashSet<User>();

    @ManyToMany(mappedBy = "myFriends")
    private Set<User> friendsOfMine = new HashSet<User>();

    @Override
    public String toString(){
        return String.format("%s, %s, %d, %s, %s, %s \n",
                name, surname, age, gender, interests, city);
    }
}
