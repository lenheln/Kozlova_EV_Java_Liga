package com.example.social_network.repository;

import com.example.social_network.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ResponseStatus
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findByName(String cityName);
}

