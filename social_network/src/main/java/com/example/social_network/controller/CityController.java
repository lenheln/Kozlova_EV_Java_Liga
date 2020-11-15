package com.example.social_network.controller;

import com.example.social_network.dto.CityOnUserPageDto;
import com.example.social_network.service.CityService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

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
     * Первыми выводятся города, где больше всего жителей.
     *
     * @param name название города (или часть названия)
     * @param pageable настройки пагинации
     * @return страница с городами, отсортированными по количеству жителей в городе.
     *
     */

    @GetMapping
    @ApiOperation(value = "Получение списка городов",
            notes = "Метод позволяет получить список городов. " +
                    "В качестве параметра можно указать название города или часть названия." +
                    "Список городов отсортирован по количеству жителей. " +
                    "Первыми выводятся города с максимальным количеством жителей.",
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список городов")
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "Название города", example = "ангарск")
    })
    public ResponseEntity<Page<CityOnUserPageDto>> findAll(
            @RequestParam(required = false) String name,
            @ApiIgnore @PageableDefault(size = 10) Pageable pageable) {
        Page<CityOnUserPageDto> cities = cityService.findAll(name, pageable);
        return new ResponseEntity(cities, HttpStatus.OK);
    }
}
