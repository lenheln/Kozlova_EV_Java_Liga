package com.example.social_network.controller;

import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.service.UserService;
import com.example.social_network.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

/**
 * Контроллер для работы с сущностью User (пользователь)
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //TODO проверка есть ли юзер с таким именем
    //TODO логи добавить
    //TODO по разным контроллерам?

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto данные пользователя в виде Dto
     * @return пользователя
     */
    @PostMapping
    public Long registration(@RequestBody @Valid UserRegisterDto userDto) throws Exception {
        log.info("Register new user={}", userDto.toString());
        return userService.save(userDto);
    }

    /**
     * Получает страницу пользователя по его id
     *
     * @param id пользователя
     * @return страницу пользователя с заданным id
     */
    @GetMapping("{id}")
    //TODO handler exception сделать
    public UserPageDto getPersonalPage(@PathVariable Long id) {
        log.info("Get page of user with id={}", id);
        return userService.getUser(id);
    }

    //TODO мы не знаем какое поле или поля будут обновляться. Значит нужно на каждое поле
    //писать метод его обновления. Либо в сервисе проверять принятые поля от dto на null
    // и копировать только те, что не null (сейчас последнее)
    //А если пользовтель хочет удалить информацию об интересах ему придется "" вставить, чтоб не было null

    /**
     * Обновляет поля на странице пользователя
     *
     * @param userDto данные пользователя
     * @param id      пользователя
     * @return id пользователя
     */
    @PatchMapping("{id}")
    public Long updatePage(@RequestBody @Valid UserEditDto userDto,
                           @PathVariable Long id) throws Exception {
        log.info("Update following info {} about user with id={}", userDto, id);
        return userService.updateUser(userDto, id);
    }

    /**
     * Удаляет страницу пользователя с указанным id
     *
     * @param id
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete user with id={}", id);
        userService.delete(id);
    }

    //TODO может все взаимодействие с френдами в отдельный контроллер выбросить?

    /**
     * Добавляет друга пользователю с userId
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     */
    @PutMapping("/friends/{userId}/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Create friendship of users id = {} and id = {}", userId, friendId);
        userService.addFriendToUser(userId, friendId);
    }

    //TODO Get all users with paging

    /**
     * Получение списка всех друзей пользователя
     *
     * @param id пользователя
     * @return список друзей
     */
    @GetMapping("/friends/{id}")
    public Set<UserByListDto> getFriends(@PathVariable Long id) {
        log.info("Get list of friends for user with id = {}", id);
        return userService.getFriends(id);
    }

    //TODO добавить Pageable pageable (в параметры метода и в findAll)

    //TODO список городов по частичному названию и их id
    //А затем для поиска друга вводить уже этот  id в запросе в фильтре

    /**
     * Поиск пользователей с помощью фильтра
     *
     * @param filter
     * @return список пользователей удовлетворяющих условиям фильтра
     */
    @GetMapping("/find")
    public List<UserByListDto> findAll(UserFilter filter, Pageable pageable) {
        return userService.findAll(filter, pageable);
    }
}
