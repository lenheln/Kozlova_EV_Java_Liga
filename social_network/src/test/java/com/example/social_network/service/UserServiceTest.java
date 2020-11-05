package com.example.social_network.service;

import com.example.social_network.domain.User;
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
import org.springframework.test.context.jdbc.Sql;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CityRepository cityRepository;

    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, cityRepository);
    }

    //TODO названия методов тестов???
    @Test
    @DisplayName("Создание учетной записи пользователя. Сохраняет пользователя в базе данных")
    void save_() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .name("Ivan")
                .surname("Ivanov")
                .build();
        User user = userService.converterUserRegisterDtoToUser(userDto);
        user.setId(1L);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Assertions.assertEquals(user.getId(), userService.save(userDto));

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Получение данных пользователя по его id")
    void getUser() {
        User user = User.builder()
                .id(1L)
                .name("Ivan")
                .surname("Ivanov")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        UserPageDto userDto = userService.convertToUserPageDto(user);
        Assertions.assertEquals(userDto, userService.getUser(1L));

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser() {
    }

    @Test
    void delete() {
    }

    @Test
    void getFriends() {
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