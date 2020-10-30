package com.example.social_network.service.filters;

import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import com.example.social_network.repository.CityRepository;
import com.example.social_network.service.Specification.BaseSpecification;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import static javax.persistence.criteria.JoinType.LEFT;

/**
 * Фильтр для пользователей
 */
@Getter
@Setter
public class UserFilter {
    /**
     * Фамилия пользователя (или ее часть)
     */
    private String surname;

    /**
     * Id города пользователя
     * чтобы найти id города по названию (или его части) надо воспользоваться запросом
     * Get /cities?name="name"
     */
    private String city;

    /**
     * Минимальный возраст пользователя
     */
    private Integer minAge;

    /**
     * Максимальный возраст пользователя
     * @return
     */
    private Integer maxAge;

    public Specification<Object> toSpecification(){
        //TODO по части названия города

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> query = cb.createQuery(User.class);
//        Root<User> root = query.from(User.class);
//        Join<User, City> bookJoin = root.join("cityId", LEFT);
//        query.select(root).where(cb.equal(bookJoin.get("name"), city));

        return Specification.where(BaseSpecification.like("surname", surname))
                .and(BaseSpecification.equal("name", city))
                .and(BaseSpecification.gt("age", minAge))
                .and(BaseSpecification.lt("age", maxAge));
    }
}
