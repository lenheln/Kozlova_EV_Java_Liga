package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервисный слой для работы с сущностью User (пользователь)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private UserRegisterDto userDto;

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto
     * @return id пользователя
     */
    //TODO можно ли сохранить сразу dto
    public Long save(UserRegisterDto userDto){
        User user = converterUserRegisterDtoToUser(userDto);
        userRepository.save(user);
        return user.getId();
    }

    //TODO другую DTO на показ анкеты. Не UserRegisterDto
    /**
     * Получение данных пользователя по его id
     *
     * @param id
     * @return страницу пользователя
     */
    @Transactional(readOnly = true)
    public UserPageDto getUser(Long id) {
        User user = userRepository.findById(id).get();
        return convertToUserPageDto(user);
    }

    /**
     * Обновляет поля пользователя
     *
     * @param userDto
     * @param id
     * @return id пользователя с обновленными полями
     */
    public Long updateUser(UserRegisterDto userDto, Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        //TODO мы не знаем какие поля изменились поэтому все проверяем на null
        if(userDto.getName() != null) { user.setName(userDto.getName()); }
        if(userDto.getSurname() != null) { user.setSurname(userDto.getSurname()); }
        if(userDto.getAge() != null) { user.setAge(userDto.getAge()); }
        if(userDto.getGender() != null) { user.setGender(userDto.getGender()); }
        if(userDto.getInterests() != null) { user.setInterests(userDto.getInterests()); }
        if(userDto.getCity() != null) { user.setCity(userDto.getCity()); }
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Удаляет страницу пользователя (пользователя из базы данных) с указанным id
     *
     * @param id
     */
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    /**
     * Конвертирует сущность DTO {@UserRegisterDto } в сущность {@User}
     *
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
     * Конвертирует сущность {@link User} в сущность DTO {@link UserRegisterDto}
     *
     * @param user
     * @return UserRegisterDto
     */
    public UserRegisterDto convertToUserRegisterDto(User user){
        return UserRegisterDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity())
                .build();
    }
    public UserPageDto convertToUserPageDto(User user){
        return UserPageDto.builder()
                .fio(String.format("%s %s", user.getName(), user.getSurname()))
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity())
                .build();
    }
}
