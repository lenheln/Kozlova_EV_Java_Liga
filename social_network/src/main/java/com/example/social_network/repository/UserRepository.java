package com.example.social_network.repository;
import com.example.social_network.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью User (пользователь)
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
//    Optional<User> findById(Long id);
//    List<User> findAllByCity(City city);
      Page<User> findAll(Specification<User> spec, Pageable pageable);
}
