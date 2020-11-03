package com.example.social_network.service;

import com.example.social_network.domain.User;
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

public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CityRepository cityRepository;

    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, cityRepository);
    }

    @Test
    @DisplayName("Сохранение пользователя")
    public void save_User_Return_UserId() throws Exception {
        UserRegisterDto userDto = new UserRegisterDto();
        User user = userService.converterUserRegisterDtoToUser(userDto);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Assertions.assertEquals(userService.save(userDto),user);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }
}
