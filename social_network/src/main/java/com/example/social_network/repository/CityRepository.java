package com.example.social_network.repository;

import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Репозиторий для работы с сущностью City
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {


//    public Page<City> findByNameContaining(String name, Pageable pageable);


    /**
     select cities.*
     from cities
     left join users
     on (users.cityid = cities.id)
     where cities.name like '%name%'
     group by cities.id
     order by count(cities.id) desc;
     */
}
