package com.example.social_network.domain;

import com.example.social_network.utils.Genders;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotNull
    @Length(max = 45)
    @Column(name = "name")
    private String name;

    @NotNull
    @Length(max = 45)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Positive
    @Max(125)
    @Column(name = "age")
    private Integer age;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Genders gender;

    @Length(max = 512)
    @Column(name = "interests")
    private String interests;

    @ManyToOne
    @JoinColumn(name="cityid")
    private City city;

    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name="friendship",
            joinColumns={@JoinColumn(name="iduser")},
            inverseJoinColumns={@JoinColumn(name="idfriend")})
    private Set<User> myFriends = new HashSet<User>();

    @ManyToMany(cascade={CascadeType.PERSIST}, mappedBy = "myFriends", fetch = FetchType.LAZY)
    private Set<User> friendsOfMine = new HashSet<User>();

    @Override
    public String toString(){
        return String.format("Name: %s, Surname: %s, Age: %d, Gender: %s, Interests: %s, City: %s \n",
                name, surname, age, gender, interests, city);
    }
}
