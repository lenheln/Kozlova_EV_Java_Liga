package com.example.social_network.service.filters;
import com.example.social_network.domain.User;
import com.example.social_network.service.Specification.BaseSpecification;
import com.example.social_network.utils.Genders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

/**
 *  Класс описывает фильтрацию пользователей по савокупности полей
 */
@Getter
@Setter
public class UserFilter {

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Фамилия пользователя (или ее часть)
     */
    private String surname;

    /**
     * Название города
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

    /**
     *  Пол пользователя
     */
    private Genders gender;

    public Specification<User> toSpecification(){

        return Specification.where(BaseSpecification.<User>equal("name", name))
                .and(BaseSpecification.like("surname", surname))
                .and(BaseSpecification.<User>equal("city","name", city))
                .and(BaseSpecification.gt("age", minAge))
                .and(BaseSpecification.lt("age", maxAge))
                .and(BaseSpecification.equal("gender", gender));
    }
}
