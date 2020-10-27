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
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     * @param userDto
     * @return сущность User (пользователь)
     */
    public User save(UserRegisterDto userDto){
        User user = converterUserRegisterDtoToUser(userDto);
        return userRepository.save(user);
    }

    //TODO другую DTO на показ анкеты. Не UserRegisterDto
    /**
     * Получение страницы пользователя по его id
     * @param id
     * @return страницу пользователя UserRegisterDto
     * @throws NoEntityException
     */
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
        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .interests(userDto.getInterests())
                .city(userDto.getCity())
                .build();
    }

    /**
     * Конвертирует сущность User в сущность UserToUserRegisterDto
     * @param user
     * @return UserToUserRegisterDto
     */
    public UserRegisterDto convertUserToUserRegisterDto(User user){
        return UserRegisterDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity())
                .build();
    }
}
