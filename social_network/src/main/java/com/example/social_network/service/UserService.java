package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.UserRepository;
import com.example.social_network.utils.NoEntityException;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.NotEmptyExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        User user = converterUserRegisterDtoToUser(userDto);
        return userRepository.save(user);
    }

    //TODO другую DTO на показ анкеты. Не UserRegisterDto
    @Transactional(readOnly = true)
    public UserRegisterDto getUser(Long id) throws NoEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoEntityException(id));
        return convertUserToUserRegisterDto(user);
    }

    //TODO посмотреть названия и как они делаются эти конвертеры dto в примере Дениса

    /**
     * Конвертирует сущность UserRegisterDto в сущность User
     * @param userDto
     * @return User
     */
    public User converterUserRegisterDtoToUser(UserRegisterDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setInterests(userDto.getInterests());
        user.setCity(userDto.getCity());
        return user;
    }

    /**
     * Конвертирует сущность User в сущность UserToUserRegisterDto
     * @param user
     * @return UserToUserRegisterDto
     */
    public UserRegisterDto convertUserToUserRegisterDto(User user){
        UserRegisterDto userDto = new UserRegisterDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setInterests(user.getInterests());
        userDto.setCity(user.getCity());
        return userDto;
    }
}
