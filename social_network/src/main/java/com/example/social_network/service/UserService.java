package com.example.social_network.service;

import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.CityRepository;
import com.example.social_network.repository.UserRepository;
import com.example.social_network.service.filters.FriendFilter;
import com.example.social_network.service.filters.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Сервисный слой для работы с сущностью User (пользователь)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    /**
     * Создание учетной записи пользователя. Сохраняет пользователя в базе данных
     *
     * @param userDto
     * @return id пользователя
     */
    public Long save(UserRegisterDto userDto) throws Exception {
        User user = converterUserRegisterDtoToUser(userDto);
        user = userRepository.save(user);
        return user.getId();
    }

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
     * Обновление полей пользователя
     *
     * @param userDto
     * @param id
     * @return id пользователя с обновленными полями
     */

    //TODO по идее если Id не найден, то нельзя прерывать программу
    public void updateUser(UserEditDto userDto, Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        //TODO мы не знаем какие поля изменились поэтому все проверяем на null.
        if(userDto.getName() != null) { user.setName(userDto.getName()); }
        if(userDto.getSurname() != null) { user.setSurname(userDto.getSurname()); }
        if(userDto.getAge() != null) { user.setAge(userDto.getAge()); }
        if(userDto.getGender() != null) { user.setGender(userDto.getGender()); }
        if(userDto.getInterests() != null) { user.setInterests(userDto.getInterests()); }
        if(userDto.getCity() != null) { user.setCity(getCityInstanceByName(userDto.getCity()));}
        userRepository.save(user);
    }

    /**
     * Удаляет страницу пользователя (пользователя из базы данных) с указанным id
     *
     * @param id
     */
    @ResponseBody
    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Set<User> friendsOfMine = user.getFriendsOfMine();
        for (User friend: friendsOfMine) {
            friend.getMyFriends().remove(user);
            userRepository.save(friend);
        };
        userRepository.flush();
        userRepository.deleteById(id);
    }

    /**
     * Получение списка всех друзей пользователя
     *
     * @param id пользователя
     * @return сет друзей
     */
    //TODO плохо, что сначала получаем юзера, а потом опять запрос идет
    public Page<UserByListDto> getFriends(Long id, FriendFilter filter, Pageable pageable){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        filter.setUser(user);
//        filter.setId(id);
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
    public void addFriend(Long userId, Long friendId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));
        user.getMyFriends().add(friend);
        userRepository.save(user);
    }

    /**
     * Удаление друза из списка друзей
     *
     * @param userId идентификатор пользователя, который совершает действие
     * @param friendId идентификатор другя
     */
    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));
        user.getMyFriends().remove(friend);
        userRepository.save(user);
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

    //TODO: а если найдено наоборот несколько городов с таким именем, то что

    /**
     * Возвращает сущность City по названию города
     * @param cityName название города
     * @return сущность City
     * @throws Exception если города с таким названием в базе нет
     */
    public City getCityInstanceByName(String cityName) throws Exception {
        if(cityName == null){
            return null;
        } else {
            return cityRepository.findByName(cityName).orElseThrow(() -> new Exception("City not found"));
        }
    }

    /**
     * Конвертирует сущность DTO {@UserRegisterDto } в сущность {@User}
     *
     * @param userDto
     * @return User
     */
    public User converterUserRegisterDtoToUser(UserRegisterDto userDto) throws Exception {
        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .interests(userDto.getInterests())
                .city(getCityInstanceByName(userDto.getCity()))
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
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity())
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
                .age(user.getAge())
                .build();
    }
}