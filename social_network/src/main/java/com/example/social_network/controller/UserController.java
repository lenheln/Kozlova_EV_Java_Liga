package com.example.social_network.controller;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.service.UserService;
import com.example.social_network.utils.NoEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    @PostMapping
    public User registration(@RequestBody UserRegisterDto userDto){
        return userService.save(userDto);
    }

    @GetMapping
    //TODO Dto тут должно быть другое. Типа UserPageDto
    //TODO handler exception сделать
    public UserRegisterDto getPersonalPage(@RequestParam Long id) throws NoEntityException {
        return userService.getUser(id);
    }

}
