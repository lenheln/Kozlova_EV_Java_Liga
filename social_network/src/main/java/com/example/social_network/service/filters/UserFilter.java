package com.example.social_network.service.filters;
import com.example.social_network.domain.User;
import com.example.social_network.service.Specification.BaseSpecification;
import com.example.social_network.utils.Genders;
import com.example.social_network.utils.KeyboardConverter;
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

        String convertedName = (name == null) ?  null : KeyboardConverter.convert(name);
        String convertedSurname = (surname == null) ?  null : KeyboardConverter.convert(surname);

        return Specification.where(
                        Specification.where(BaseSpecification.<User>equal("name", name))
                        .or(BaseSpecification.equal("name", convertedName))
                        )
                        .and(
                                Specification.where(BaseSpecification.<User>like("surname", surname))
                                .or(BaseSpecification.<User>like("surname", convertedSurname))
                        )
                        .and(BaseSpecification.<User>equal("city","name", city))
                        .and(BaseSpecification.gt("age", minAge))
                        .and(BaseSpecification.lt("age", maxAge))
                        .and(BaseSpecification.equal("gender", gender));
    }
}
