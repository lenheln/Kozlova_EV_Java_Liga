package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервисный слой для работы с сущностью User (пользователь)
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Сохраняет сущность User (пользователь) в базе данных
     * @param userDto
     * @return сущность User (пользователь)
     */
    public User save(UserRegisterDto userDto){
        User user = converterUserRegisterDto(userDto);
        return userRepository.save(user);
    }

    //TODO посмотреть названия и как они делаются эти конвертеры dto в примере Дениса
    public User converterUserRegisterDto(UserRegisterDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setInterests(userDto.getInterests());
        user.setCity(userDto.getCity());
        return user;
    }
}
