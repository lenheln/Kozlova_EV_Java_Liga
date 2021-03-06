package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegistrationDto;
import com.example.social_network.repository.UserRepository;
import com.example.social_network.service.filters.FriendFilter;
import com.example.social_network.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Сервисный слой для работы с сущностью User (пользователь)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CityService cityService;

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto
     * @return id пользователя
     */
    public Long save(UserRegistrationDto userDto) throws Exception {
        User user = converterUserRegisterDtoToUser(userDto);
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * Получение страницы пользователя по его id
     *
     * @param id идентификатор пользователя
     * @return страница пользователя
     */
    public UserPageDto getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        return convertToUserPageDto(user);
    }

    /**
     * Обновление полей пользователя
     *
     * @param userDto поля для обновления
     * @param id идентификатор пользователя
     * @return id пользователя с обновленными полями
     */

    public void updateUser(UserEditDto userDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if(userDto.getName() != null) { user.setName(userDto.getName()); }
        if(userDto.getSurname() != null) { user.setSurname(userDto.getSurname()); }
        if(userDto.getDateOfBDay() != null) { user.setDateOfBDay(userDto.getDateOfBDay()); }
        if(userDto.getGender() != null) { user.setGender(userDto.getGender()); }
        if(userDto.getInterests() != null) { user.setInterests(userDto.getInterests()); }
        if(userDto.getCity() != null) { user.setCity(userDto.getCity());}
        userRepository.save(user);
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
     * Получение списка всех друзей пользователя
     *
     * @param id пользователя
     * @return сет друзей
     */
    public Page<UserByListDto> getFriends(Long id, FriendFilter filter, Pageable pageable){
        filter.setId(id);
        return userRepository
                .findAll(filter.toSpecification(), pageable)
                .map(this::convertToUserByListDto);
    }

    /**
     * Добавление друга пользователю с userId
     *
     * @param userId идентификатор пользователя
     * @param friendId идентификатор друга
     */
    public void addFriend(Long userId, Long friendId) {
        userRepository.addFriend(userId, friendId);
    }

    /**
     * Удаление друга из списка друзей
     *
     * @param userId идентификатор пользователя, который совершает действие
     * @param friendId идентификатор другя
     */
    public void deleteFriend(Long userId, Long friendId) {
        userRepository.deleteFriend(userId, friendId);
    }

    /**
     * Поиск пользователей по фильтрам
     *
     * @param filter набор условий
     * @param pageable настройки пагинации
     * @return список юзеров в виде {@UserByListDto}
     */
    public Page<UserByListDto> findAll(UserFilter filter, Pageable pageable) {
        return userRepository
                .findAll(filter.toSpecification(), pageable)
                .map(this::convertToUserByListDto);
    }

    /**
     * Конвертирует сущность DTO {@UserRegisterDto } в сущность {@User}
     *
     * @param userDto
     * @return User
     */
    public User converterUserRegisterDtoToUser(UserRegistrationDto userDto) throws Exception {
        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .dateOfBDay(userDto.getDateOfBDay())
                .gender(userDto.getGender())
                .city(userDto.getCity())
                .build();
    }

    /**
     * Конвертирует сущность {@link User} в сущность DTO {@link UserPageDto}
     * для отображения на странице пользователя
     *
     * @param user
     * @return UserPageDto
     */
    public UserPageDto convertToUserPageDto(User user) {

        return UserPageDto.builder()
                .fio(String.format("%s %s", user.getName(), user.getSurname()))
                .age(Period.between(user.getDateOfBDay(), LocalDate.now()).getYears())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(cityService.convertToCityOnPageDto(user.getCity()))
                .friends(Stream.concat(
                        //TODO тут надо как-то вынуть всех друзей (через репозиторий например а там скрипт на jpql например)
                        user.getMyFriends().stream(),
                        user.getMyFriends().stream()
                )
                        .collect(Collectors.toList())
                        .stream()
                        .map(this::convertToUserByListDto)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Конвертирует сущность {@link User} в сущность DTO {@link UserByListDto}
     * для отображения в списке друзей
     *
     * @param user
     * @return UserByListDto
     */
    public UserByListDto convertToUserByListDto(User user){
        return UserByListDto.builder()
                .fio(String.format("%s %s", user.getName(), user.getSurname()))
                .gender(user.getGender())
                .age(Period.between(user.getDateOfBDay(), LocalDate.now()).getYears())
                .build();
    }
}