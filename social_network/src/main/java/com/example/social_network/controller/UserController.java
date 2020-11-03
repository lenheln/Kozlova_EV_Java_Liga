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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    //TODO логи добавить

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto данные пользователя в виде Dto
     * @return пользователя
     */
    @PostMapping
    @ApiOperation("Создание учетной записи пользователя. Сохраняет пользователя в базе данных")
    public Long createPage(@RequestBody @Valid UserRegisterDto userDto) throws Exception {
        log.info("Register new user={}", userDto.toString());
        return userService.save(userDto);
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
    public Page<UserByListDto> getUsers(UserFilter filter,
                                        @ApiIgnore @PageableDefault(size = 3) Pageable pageable) {
        log.info("Получение списка пользователей с помощью фильтра");
        return userService.findAll(filter, pageable);
    }


    /**
     * Получение страницы пользователя по его id
     *
     * @param id пользователя
     * @return страницу пользователя с заданным id
     */
    @GetMapping("{id}")
    @ApiOperation("Получение страницы пользователя по его id")
    //TODO handler exception сделать
    public UserPageDto getPage(@PathVariable Long id) {
        log.info("Get page of user with id={}", id);
        return userService.getUser(id);
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
    public Long updatePage(@RequestBody @Valid UserEditDto userDto,
                           @PathVariable Long id) throws Exception {
        log.info("Update following info {} about user with id={}", userDto, id);
        return userService.updateUser(userDto, id);
    }


    /**
     * Удаление страницы пользователя с указанным id
     *
     * @param id
     */
    @DeleteMapping("{id}")
    @ApiOperation("Удаление страницы пользователя с указанным id")
    public void deletePage(@PathVariable Long id) {
        log.info("Delete user with id={}", id);
        userService.delete(id);
    }

    /**
     * Добавление друга пользователю с userId
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     */
    @PutMapping("/{userId}/add")
    @ApiOperation("Добавление друга пользователю с userId")
    public void addFriend(@PathVariable Long userId, @RequestParam Long friendId) {
        log.info("Create friendship of users id = {} and id = {}", userId, friendId);
        userService.addFriend(userId, friendId);
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
    public Page<UserByListDto> getFriends(@PathVariable Long userId,
                                          FriendFilter filter,
                                          @ApiIgnore @PageableDefault(size = 3) Pageable pageable) {
        log.info("Get list of friends for user with id = {}", userId);
        return userService.getFriends(userId, filter, pageable);
    }

    /**
     * Удаление друга из списка друзей
     *
     * @param userId идентификатор пользователя, который совершает действие
     * @param friendId идентификатор другя
     */
    @PutMapping("/{userId}/friends/delete")
    @ApiOperation("Удаление друга из списка друзей")
    public void deleteFriend(@PathVariable Long userId, @RequestParam Long friendId){
        log.info("Delete friendship of users id = {} and id = {}", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    //TODO список городов по частичному названию и их id
}
