package com.example.social_network.service.filters;

import com.example.social_network.service.Specification.BaseSpecification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

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
     * Город пользователя
     */
//    private String cityName;

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
        return Specification.where(BaseSpecification.like("surname", surname))
                .and(BaseSpecification.gt("age", minAge))
                .and(BaseSpecification.lt("age", maxAge));
    }
}
