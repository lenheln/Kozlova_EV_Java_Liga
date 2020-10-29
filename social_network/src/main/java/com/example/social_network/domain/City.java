package com.example.social_network.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "city")
//    private Set<User> users;
}