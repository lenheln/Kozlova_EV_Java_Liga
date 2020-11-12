package com.example.social_network.controller;

import com.example.social_network.dto.CityOnUserPageDto;
import com.example.social_network.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Контроллер для работы с сущностью City
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/cities")
@Api(description = "Контроллер для работы с сущностью City")
public class CityController {

    private final CityService cityService;

    /**
     * Получение списка городов
     *
     * @param name название города (или часть названия)
     * @param pageable настройки пагинации
     * @return страница с городами, отсортированными по количеству жителей в городе.
     *      * Первыми выводятся города, где больше всего жителей.
     */
    @GetMapping
    @ApiOperation("Получение списка городов")
    public Page<CityOnUserPageDto> findAll(@RequestParam(required = false) String name,
                                             @ApiIgnore @PageableDefault(size = 10) Pageable pageable) {
        return cityService.findAll(name, pageable);
    }
}
