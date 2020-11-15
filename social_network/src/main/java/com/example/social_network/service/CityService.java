package com.example.social_network.service;

import com.example.social_network.domain.City;
import com.example.social_network.domain.Region;
import com.example.social_network.domain.User;
import com.example.social_network.dto.CityOnUserPageDto;
import com.example.social_network.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

/**
 * Сервисный слой для работы с сущностью City
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;


    /**
     * Поиск городов по вхождению строки в название города
     *
     * @param name название города (или часть названия)
     * @param pageable настройки пагинации
     * @return страница с городами, отсортированными по количеству жителей в городе.
     * Первыми выводятся города, где больше всего жителей.
     */
    public Page<CityOnUserPageDto> findAll(String name, Pageable pageable) {

        Specification<City> specification = (root, query, cb) -> {
                Join<City, User> joinUsers = root.join("users", JoinType.LEFT);
                query.multiselect(joinUsers)
                        .groupBy(root.get("id"))
                        .orderBy(cb.desc(cb.count(joinUsers.get("id"))));
                return (name == null)
                        ? null
                        : cb.like(cb.lower(root.get("name")), cb.lower(cb.literal("%" + name + "%")));
        };
        return cityRepository.findAll(specification, pageable)
                             .map(this::convertToCityOnPageDto);
    }

    /**
     * Конвертирует сущность City в Dto для отображения в списке городов
     *
     * @param city
     * @return dto
     */
    public CityOnUserPageDto convertToCityOnPageDto(City city){

        if(city == null) { return null; }
        CityOnUserPageDto cityDto = new CityOnUserPageDto();
        cityDto.setName(city.getName());
        Region region = city.getRegion();
        if (region != null) {
            cityDto.setRegionName(region.getName());
        }
        return cityDto;
    }
}
