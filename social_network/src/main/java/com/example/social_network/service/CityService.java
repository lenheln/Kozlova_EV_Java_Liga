package com.example.social_network.service;

import com.example.social_network.domain.City;
import com.example.social_network.domain.Region;
import com.example.social_network.dto.CityOnUserPageDto;
import com.example.social_network.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервисный слой для работы с сущностью City
 */
@Service
@AllArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    /**
     * Поиск городов по вхождению строки в название города
     *
     * @param name поисковый запрос
     * @return список dto удовлетворяющих запросу
     */

    public Page<CityOnUserPageDto> findCityByName(String name, Pageable pageable) {
        return cityRepository
                .findByNameContaining(name, pageable)
                .map(this::convertToCityOnPageDto);
    }

    /**
     * Конвертирует сущность City в Dto для отображения в списке городов
     *
     * @param city
     * @return dto
     */
    public CityOnUserPageDto convertToCityOnPageDto(City city){
        CityOnUserPageDto cityDto = new CityOnUserPageDto();
        cityDto.setName(city.getName());
        Region region = city.getRegion();
        if (region != null) {
            cityDto.setRegionName(region.getName());
        }
        return cityDto;
    }
}
