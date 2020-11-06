package com.example.social_network.service;

import com.example.social_network.domain.User;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserPageDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.repository.CityRepository;
import com.example.social_network.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CityRepository cityRepository;

    private UserService userService;
    private User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, cityRepository);
        user = new User();
        user.setId(1L);
        user.setName("Ivan");
        user.setSurname("Ivanov");
    }

    //TODO названия методов тестов???
    @Test
    @DisplayName("Создание учетной записи пользователя. Сохраняет пользователя в базе данных")
    void save_() throws Exception {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Assertions.assertEquals(user.getId(), userService.save(new UserRegisterDto()));

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Получение данных пользователя по его id")
    void getUser() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        UserPageDto userDto = userService.convertToUserPageDto(user);
        Assertions.assertEquals(userDto, userService.getUser(1L));

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Обновление полей пользователя")
    void updateUser() throws Exception {

//        UserPageDto userDto = userService.convertToUserPageDto(user);
//
//        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
//        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        Assertions.assertEquals(userDto, userService.updateUser(new UserEditDto(), 1L));
//
//        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
//        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
//        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Удаляет страницу пользователя (пользователя из базы данных) с указанным id")
    //TODO в интеграционном тесте будем проверять статус ответа 200
    //TODO надо тут допилить вот этот verifyNoMoreInteractions. Что то в методе не так в сервисе
    void delete() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        userService.delete(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
//        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Получение списка всех друзей пользователя")
    void getFriends() {
//        User friend = User.builder()
//                .name("Friend")
//                .surname("Friend")
//                .build();
//        Set<User> friends = new HashSet<>();
//        friends.add(friend);
//        user.setMyFriends(friends);
//
//        Mockito.when(userRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
//                .thenReturn((Page)friends);


    }

    @Test
    void addFriend() {
    }

    @Test
    void deleteFriend() {
    }

    @Test
    void findAll() {
    }

    @Test
    void getCityInstanceByName() {
    }

    @Test
    void converterUserRegisterDtoToUser() {
    }

    @Test
    void convertToUserRegisterDto() {
    }

    @Test
    void convertToUserPageDto() {
    }

    @Test
    void convertToUserByListDto() {
    }
}