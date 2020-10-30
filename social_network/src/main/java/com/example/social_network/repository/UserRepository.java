package com.example.social_network.repository;
import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User (пользователь)
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);
    List<User> findAllByCity(City city);
    List<User> findAll(Specification<Object> spec);
}
