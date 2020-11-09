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

    /**
     * Составляет спецификацию по всем фильтрам
     *
     * @return спецификацию по всем фильтрам
     */
    public Specification<User> toSpecification() {

        Specification<User> fioSpecification = null;
        if(fio != null) {
            String[] fioPartly = fio.split(" ");
            fioSpecification = appendSpecifications(fioPartly.length-1, fioPartly);
        }
        return Specification.where(fioSpecification)
                    .and(BaseSpecification.equalCity("city", "id", city))
                    .and(BaseSpecification.gt("age", minAge))
                    .and(BaseSpecification.lt("age", maxAge))
                    .and(BaseSpecification.equal("gender", gender));
    }

    /**
     * Составляет единое выражение - спецификацию для всех токенов из поля fio
     *
     * @param i порядковый номер токена
     * @param fio массив токенов на которые разбито поле fio
     * @return единую спецификацию
     */
    public Specification<User> appendSpecifications(int i, String[] fio) {
        while(i > 0) {
            return appendSpecifications(i - 1, fio)
                    .and(createBaseSpecification(fio[i]));
        }
        return Specification.where(
                createBaseSpecification(fio[0])
        );
    }

    /**
     * Составляет базовую спецификацию для одного токена из строки fio.
     * Базовая спецификация проверяет присутствие токена в имени или фамилии пользователя.
     *
     * @param fioPart токен из строки fio
     * @return базовую спецификацию для токена из строки fio
     */
    public Specification<User> createBaseSpecification(String fioPart) {
        return Specification.where(BaseSpecification.<User>like("name", fioPart))
                .or(BaseSpecification.<User>like("surname", fioPart));
    }
}
