package com.example.social_network.controller;

import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.service.UserService;
import com.example.social_network.service.filters.FriendFilter;
import com.example.social_network.service.filters.UserFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с сущностью User (пользователь)
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Api(description = "Контроллер для работы с сущностью User (пользователь)")
public class UserController {

    private final UserService userService;

    //TODO проверка есть ли юзер с таким именем
    //TODO логи добавить и проверить (они должны все ошибки записывать)
    //TODO add response??? 404 - там где ошибки типа User not found
    //TODO возвращать только респонсы, чтобы не перегружать поток данными
    //TODO @ExceptionHandler

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto данные пользователя в виде Dto
     * @return пользователя
     */
    @PostMapping
//TODO обработку валидации. Если невалидные значения, то самим написать что не так
    @ApiOperation("Создание учетной записи пользователя. Сохраняет пользователя в базе данных")
    public ResponseEntity createPage(@RequestBody @Valid UserRegisterDto userDto) throws Exception {
        log.info("Register new user={}", userDto.toString());
        Long id = userService.save(userDto);
        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    /**
     * Поиск пользователей с помощью фильтра
     *
     * @param filter настройки фильтрации
     * @param pageable настройки пагинации
     * @return список пользователей удовлетворяющих условиям фильтра
     */
    @GetMapping()
    @ApiOperation("Поиск пользователей с помощью фильтра")
    public ResponseEntity getUsers(UserFilter filter,
                                        @ApiIgnore @PageableDefault(size = 3) Pageable pageable) {
        log.info("Get list of users");
        Page<UserByListDto> page = userService.findAll(filter, pageable);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    /**
     * Получение страницы пользователя по его id
     *
     * @param id пользователя
     * @return страницу пользователя с заданным id
     */
    @GetMapping("{id}")
    @ApiOperation("Получение страницы пользователя по его id")
    public ResponseEntity getPage(@PathVariable Long id) {
        log.info("Get page of user with id={}", id);
        UserPageDto userDto = userService.getUser(id);
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    //TODO мы не знаем какое поле или поля будут обновляться. Значит нужно на каждое поле
    //писать метод его обновления. Либо в сервисе проверять принятые поля от dto на null
    // и копировать только те, что не null (сейчас последнее)
    //А если пользовтель хочет удалить информацию об интересах ему придется "" вставить, чтоб не было null

    /**
     * Обновление полей на странице пользователя
     *
     * @param userDto данные пользователя
     * @param id      пользователя
     * @return id пользователя
     */
    @PatchMapping("{id}")
    @ApiOperation("Обновление полей на странице пользователя")
    public ResponseEntity updatePage(@RequestBody @Valid UserEditDto userDto,
                           @PathVariable Long id) throws Exception {
        log.info("Update following info {} about user with id={}", userDto, id);
        userService.updateUser(userDto, id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Удаление страницы пользователя с указанным id
     *
     * @param id
     */
    @DeleteMapping("{id}")
    @ApiOperation("Удаление страницы пользователя с указанным id")
    public ResponseEntity deletePage(@PathVariable Long id) {
        log.info("Delete user with id={}", id);
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Добавление друга пользователю с userId
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     */
    @PutMapping("/{userId}/add")
    @ApiOperation("Добавление друга пользователю с userId")
    public ResponseEntity addFriend(@PathVariable Long userId, @RequestParam Long friendId) {
        log.info("Create friendship of users id = {} and id = {}", userId, friendId);
        userService.addFriend(userId, friendId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Получение списка друзей пользователя с помощью фильтра
     * @param userId идентификатор пользователя
     * @param filter фильтры
     * @param pageable
     * @return страницу с друзьями
     */
    @GetMapping("/{userId}/friends")
    @ApiOperation("Получение списка друзей пользователя с помощью фильтра")
    public ResponseEntity getFriends(@PathVariable Long userId,
                                          FriendFilter filter,
                                          @ApiIgnore @PageableDefault(size = 3) Pageable pageable) {
        log.info("Get list of friends for user with id = {}", userId);
        Page<UserByListDto> page = userService.getFriends(userId, filter, pageable);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    /**
     * Удаление друга из списка друзей
     *
     * @param userId идентификатор пользователя, который совершает действие
     * @param friendId идентификатор другя
     */
    @PutMapping("/{userId}/friends/delete")
    @ApiOperation("Удаление друга из списка друзей")
    public ResponseEntity deleteFriend(@PathVariable Long userId, @RequestParam Long friendId){
        log.info("Delete friendship of users id = {} and id = {}", userId, friendId);
        userService.deleteFriend(userId, friendId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //TODO список городов по частичному названию и их id
}
