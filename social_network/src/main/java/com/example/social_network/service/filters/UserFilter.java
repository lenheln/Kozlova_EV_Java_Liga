package com.example.social_network.service.filters;
import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import com.example.social_network.service.Specification.BaseSpecification;
import com.example.social_network.utils.Genders;
import com.example.social_network.utils.KeyboardConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

/**
 *  Фильтрует пользователей по полям-критериям
 */
@Getter
@Setter
public class UserFilter {

    /**
     * Фио пользователя
     */
    private String fio;

    /**
     * Сущность город
     */
    private City city;

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

    public Specification<User> toSpecification() {
        
        Specification<User> resultSpec = null;
        if(fio != null) {
            String[] fioPartly = fio.split(" ");
            resultSpec = concatSpecifications(fioPartly.length-1, fioPartly);
        }
        return Specification.where(resultSpec)
                    .and(BaseSpecification.equalCity("city", "id", city))
                    .and(BaseSpecification.gt("age", minAge))
                    .and(BaseSpecification.lt("age", maxAge))
                    .and(BaseSpecification.equal("gender", gender));

    }

    public Specification<User> concatSpecifications(int i, String[] fio) {
        while(i > 0) {
            return concatSpecifications(i - 1, fio).and(createSpecification(fio[i]));
        }
        return Specification.where(
                createSpecification(fio[0])
        );
    }

    public Specification<User> createSpecification(String fioPart) {
        return Specification.where(BaseSpecification.<User>like("name", fioPart))
                .or(BaseSpecification.<User>like("surname", fioPart));
    }
}
