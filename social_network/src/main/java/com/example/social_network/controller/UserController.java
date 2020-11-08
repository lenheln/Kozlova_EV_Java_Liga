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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


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

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto данные пользователя в виде Dto
     * @return пользователя
     */
    @PostMapping
    @ApiOperation("Создание учетной записи пользователя. Сохраняет пользователя в базе данных")
    public ResponseEntity createPage(@RequestBody @Valid UserRegisterDto userDto) throws Exception {
        Long id = userService.save(userDto);
        log.info("Register new user={}", userDto.toString());
        String message = String.format("User with id = %d created", id);
        return new ResponseEntity(message, HttpStatus.CREATED);
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

    /**
     * Обновление полей на странице пользователя
     *
     * @param userDto данные пользователя
     * @param id      пользователя
     * @return id пользователя
     */
    @PatchMapping("{id}")
    @ApiOperation("Обновление полей на странице пользователя")
    public ResponseEntity updatePage(@RequestBody @Valid UserEditDto userDto, @PathVariable Long id) {
        userService.updateUser(userDto, id);
        log.info("Update following info {} about user with id={}", userDto, id);
        String message = String.format("User with id = %d updated", id);
        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     * Удаление страницы пользователя с указанным id
     *
     * @param id
     */
    @DeleteMapping("{id}")
    @ApiOperation("Удаление страницы пользователя с указанным id")
    public ResponseEntity deletePage(@PathVariable Long id) {
        userService.delete(id);
        log.info("Delete user with id={}", id);
        String message = String.format("User with id = %d deleted", id);

        return new ResponseEntity(message, HttpStatus.OK);
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
        userService.addFriend(userId, friendId);
        log.info("Create friendship of users id = {} and id = {}", userId, friendId);
        String message = String.format("User with id = %d added as a friend", friendId);

        return new ResponseEntity(message, HttpStatus.OK);
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
        userService.deleteFriend(userId, friendId);
        log.info("Delete friendship of users id = {} and id = {}", userId, friendId);
        String message = String.format("User with id = %d deleted from friends", friendId);

        return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     * Обработчик ошибок валидации полей
     *
     * @param ex исключение
     * @return исключение в тестовом виде
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
