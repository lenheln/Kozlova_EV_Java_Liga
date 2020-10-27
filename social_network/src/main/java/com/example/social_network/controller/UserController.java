package com.example.social_network.controller;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с сущностью User (пользователь)
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //TODO проверка есть ли юзер с таким именем

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     * @param userDto
     * @return пользователя
     */
    @PostMapping
    public User registration(@RequestBody UserRegisterDto userDto){
        return userService.save(userDto);
    }

    /**
     * Получает страницу пользователя по его id
     * @param id
     * @return страницу пользователя с заданным id
     */
    @GetMapping("{id}")
    //TODO Dto тут должно быть другое. Типа UserPageDto
    //TODO handler exception сделать
    public UserRegisterDto getPersonalPage(@PathVariable Long id) {
        return userService.getUser(id);
    }

    //TODO мы не знаем какое поле или поля будут обновляться. Значит нужно на каждое поле
    //писать метод его обновления. Либо в сервисе проверять принятые поля от dto на null
    // и копировать только те, что не null (сейчас последнее)
    //А если пользовтель хочет удалить информацию об интересах ему придется "" вставить, чтоб не было null

    /**
     * Обновляет поля на странице пользователя
     * @param userDto
     * @param id
     * @return обновленную страницу
     */
    @PatchMapping("{id}")
    public UserRegisterDto updatePage(@RequestBody UserRegisterDto userDto, @PathVariable Long id){
        return userService.updateUser(userDto,id);
    }

    /**
     * Удаляет страницу пользователя с указанным id
     * @param id
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

}
