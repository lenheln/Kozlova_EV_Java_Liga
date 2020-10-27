package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @return сущность User (пользователь)
     */
    //TODO можно ли сохранить сразу dto
    public User save(UserRegisterDto userDto){
        User user = converterUserRegisterDtoToUser(userDto);
        return userRepository.save(user);
    }

    //TODO другую DTO на показ анкеты. Не UserRegisterDto
    /**
     * Получение пользователя по его id
     *
     * @param id
     * @return страницу пользователя
     */
    @Transactional(readOnly = true)
    public UserRegisterDto getUser(Long id) {
        User user = userRepository.findById(id).get();
        return convertToUserRegisterDto(user);
    }


    /**
     * Обновляет поля пользователя
     *
     * @param userDto
     * @param id
     * @return пользователя с обновленными полями
     */
    public UserRegisterDto updateUser(UserRegisterDto userDto, Long id){
        User user = userRepository.findById(id).get();
        //TODO мы не знаем какие поля изменились поэтому все проверяем на null
        if(userDto.getName() != null) { user.setName(userDto.getName()); }
        if(userDto.getSurname() != null) { user.setSurname(userDto.getSurname()); }
        if(userDto.getAge() != null) { user.setAge(userDto.getAge()); }
        if(userDto.getGender() != null) { user.setGender(userDto.getGender()); }
        if(userDto.getInterests() != null) { user.setInterests(userDto.getInterests()); }
        if(userDto.getCity() != null) { user.setCity(userDto.getCity()); }
        user = userRepository.save(user);
        return convertToUserRegisterDto(user);
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
}
