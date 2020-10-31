package com.example.social_network.controller;

import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.service.UserService;
import com.example.social_network.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * Поиск пользователей с помощью фильтра
     *
     * @param filter настройки фильтрации
     * @param pageable настройки пагинации
     * @return список пользователей удовлетворяющих условиям фильтра
     */
    @GetMapping()
    public Page<UserByListDto> findAll(UserFilter filter,
                                       @PageableDefault(size = 3) Pageable pageable) {
        log.info("Получение списка пользователей с помощью фильтра");
        return userService.findAll(filter, pageable);
    }


    /**
     * Получает страницу пользователя по его id
     *
     * @param id пользователя
     * @return страницу пользователя с заданным id
     */
    @GetMapping("{id}")
    //TODO handler exception сделать
    //TODO а зачем нам по id страницу получать?
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

    /**
     * Добавляет друга пользователю с userId
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     */
    @PutMapping("/{userId}/add")
    public void addFriend(@PathVariable Long userId, @RequestParam Long friendId) {
        log.info("Create friendship of users id = {} and id = {}", userId, friendId);
        userService.addFriend(userId, friendId);
    }

    /**
     * Получение списка всех друзей пользователя
     *
     * @param userId пользователя
     * @return список друзей
     */
    //TODO пагинация, поиск по фамилии, части ее, фича с переводом на другой язык
//    @GetMapping("/{userId}/friends")
//    public List<UserByListDto> getFriends(@PathVariable Long userId,
//                                          @PageableDefault(size = 3) Pageable pageable) {
//        log.info("Get list of friends for user with id = {}", userId);
//        return userService.getFriends(userId, pageable);
//    }

    //TODO поиск друга по фильтрам среди друзей
    @GetMapping("/{userId}/friends")
    public List<UserByListDto> getFriends(@PathVariable Long userId,
                                          UserFilter filter,
                                          @PageableDefault(size = 3) Pageable pageable) {
        log.info("Get list of friends for user with id = {}", userId);
        return userService.getFriends(userId, filter, pageable);
    }/**
     * Удаление друга из списка друзей
     *
     * @param userId идентификатор пользователя, который совершает действие
     * @param friendId идентификатор другя
     */
    @PutMapping("/{userId}/friends/delete")
    public void deleteFriend(@PathVariable Long userId, @RequestParam Long friendId){
        log.info("Delete friendship of users id = {} and id = {}", userId, friendId);
        userService.deleteFriend(userId, friendId);
    }

    //TODO список городов по частичному названию и их id
    //А затем для поиска друга вводить уже этот  id в запросе в фильтре

}
