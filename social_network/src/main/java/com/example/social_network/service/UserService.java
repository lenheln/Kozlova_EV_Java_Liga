package com.example.social_network.service;

import com.example.social_network.domain.City;
import com.example.social_network.domain.User;
import com.example.social_network.dto.UserByListDto;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.CityRepository;
import com.example.social_network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /*
        TODO вот ввели тут какой-то город при регистрации, а его нет в базе, то что?
     */
    public Long save(UserRegisterDto userDto) throws Exception {
        User user = converterUserRegisterDtoToUser(userDto);
        userRepository.save(user);
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
     * Обновляет поля пользователя
     *
     * @param userDto
     * @param id
     * @return id пользователя с обновленными полями
     */
    public Long updateUser(UserEditDto userDto, Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        //TODO мы не знаем какие поля изменились поэтому все проверяем на null.
        if(userDto.getName() != null) { user.setName(userDto.getName()); }
        if(userDto.getSurname() != null) { user.setSurname(userDto.getSurname()); }
        if(userDto.getAge() != null) { user.setAge(userDto.getAge()); }
        if(userDto.getGender() != null) { user.setGender(userDto.getGender()); }
        if(userDto.getInterests() != null) { user.setInterests(userDto.getInterests()); }
//        if(userDto.getCity() != null) { user.setCity(userDto.getCity()); }
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
     * Получение списка всех друзей пользователя
     *
     * @param id пользователя
     * @return сет друзей
     */
    public Set<UserByListDto> getFriends(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        Set<UserByListDto> friends = new HashSet<>();
        user.getMyFriends().forEach(user1 -> { friends.add(convertToUserByListDto(user1)); });
        user.getFriendsOfMine().forEach(user1 -> {friends.add(convertToUserByListDto(user1));});
        return friends;
    }

    /**
     * Добавление друга пользователю с userId
     *
     * @param userId идентификатор пользователя
     * @param friendId идентификатор друга
     */
    public void addFriendToUser(Long userId, Long friendId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

        Set<User> friends = new HashSet<>();
        friends.addAll(user.getMyFriends());
        friends.addAll(user.getFriendsOfMine());
        if(friends.contains(friend)){
            throw  new RuntimeException("Friend has already added");
        } else {
            user.getMyFriends().add(friend);
            userRepository.save(user);
        }
    }

    public List<UserByListDto> findUsersByCity(String cityName){
        //TODO если в поиске забили город, а их несколько с таким именем, то тут будет эксепшн
        //TODO по идее тогда надо возвращать список этих городов с регионами и просить уточнить запрос
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if(optionalCity.isEmpty()) {
            return null;
        } else {
            City city = optionalCity.get();
            List<User> users = userRepository.findAllByCity(city);
            List<UserByListDto> userByListDtos = new ArrayList<>();
            users.forEach(user -> userByListDtos.add(convertToUserByListDto(user)));
            return userByListDtos;
        }
//        UserByListDto userByListDto = convertToUserByListDto(userRepository.findById(1L).get());
//        List<UserByListDto> list = new ArrayList<>();
//        list.add(userByListDto);
//        return list;
    }

    /**
     * Конвертирует сущность DTO {@UserRegisterDto } в сущность {@User}
     *
     * @param userDto
     * @return User
     */
    public User converterUserRegisterDtoToUser(UserRegisterDto userDto) throws Exception {
        String cityName = userDto.getCity();
        Optional<City> cityOptional = cityRepository.findByName(cityName);
        //TODO: а если найдено наоборот несколько городов с таким именем, то что
        //Может тогда поле регион тоже надо заполнять
        City city = new City();
        if(cityOptional.isEmpty()){
            throw new Exception("Указанного города не существует");
        } else {
            city = cityOptional.get();
        }
        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .interests(userDto.getInterests())
                .city(city)
                .build();
    }

    /**
     * Конвертирует сущность {@link User} в сущность DTO {@link UserRegisterDto}
     * для отображения на форме регистрации
     *
     * @param user
     * @return UserRegisterDto
     */
    //TODO лишнее?
    public UserRegisterDto convertToUserRegisterDto(User user){
        return UserRegisterDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity().getName())
                .build();
    }

    /**
     * Конвертирует сущность {@link User} в сущность DTO {@link UserPageDto}
     * для отображения на странице пользователя
     *
     * @param user
     * @return UserPageDto
     */
    public UserPageDto convertToUserPageDto(User user){
        return UserPageDto.builder()
                .fio(String.format("%s %s", user.getName(), user.getSurname()))
                .age(user.getAge())
                .gender(user.getGender())
                .interests(user.getInterests())
                .city(user.getCity().getName())
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
                .city(user.getCity().getName())
                .build();
    }
}
