package com.example.social_network.controller;

import com.example.social_network.domain.City;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.utils.Genders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *  Интеграционные тесты для контроллера
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Создание учетной записи пользователя. Возвращает статус 201")
    public void createPage_ReturnOk() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .name("Name")
                .surname("Surname")
                .age(30)
                .gender(Genders.M)
                .city(City.builder()
                        .id(34L)
                        .name("г Москва")
                        .build())
                .interests("Programming")
                .build();

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User with id = 4 created"));
    }

    @Test
    @DisplayName("Создание учетной записи пользователя.Сохраняет пользователя в базе данных")
    void createPage_InValidName_ThrowException() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .name("")
                .surname("surname")
                .build();
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", Is.is("length must be between 1 and 45")))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Получение пользователей с применением фильтра по всем полям")
    public void getUsers_WithFilter_ReturnUser() throws Exception {
        mockMvc.perform(get("/users").contentType("application/json")
                .param("page", "0")
                .param("size", "3")
                .param("name", "User1")
                .param("surname", "Surname1")
                .param("age", "33")
                .param("gender", "F")
                .param("city.id", "1")
                .param("interests", "books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fio", Is.is("User1 Surname1")))
                .andExpect(jsonPath("$.content[0].age", Is.is(33)))
                .andExpect(jsonPath("$.content[0].gender", Is.is("F")));
    }

    @Test
    @DisplayName("Получение пользователей без фильтра. Пагинация default")
    public void getUsers_ReturnUsersList() throws Exception {
         mockMvc.perform(get("/users").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fio", Is.is("User1 Surname1")))
                .andExpect(jsonPath("$.content[0].age", Is.is(33)))
                .andExpect(jsonPath("$.content[0].gender", Is.is("F")))

                .andExpect(jsonPath("$.content[1].fio", Is.is("User2 Surname2")))
                .andExpect(jsonPath("$.content[1].age", Is.is(90)))
                .andExpect(jsonPath("$.content[1].gender", Is.is("M")))

                .andExpect(jsonPath("$.content[2].fio", Is.is("User3 Surname3")))
                .andExpect(jsonPath("$.content[2].age", Is.is(50)))
                .andExpect(jsonPath("$.content[2].gender", Is.is("M")));
    }

    @Test
    @DisplayName("Получение страницы пользователя по его id")
    public void getPage_id1_PageUser1() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fio", Is.is("User1 Surname1")))
                .andExpect(jsonPath("$.age", Is.is(33)))
                .andExpect(jsonPath("$.gender", Is.is("F")))
                .andExpect(jsonPath("$.interests", Is.is("books")))
                .andExpect(jsonPath("$.city.name", Is.is("г Барнаул")));
    }

    @Test
    @DisplayName("Обновление полей на странице пользователя")
    public void updatePage_isOk() throws Exception {
        UserEditDto userDto = UserEditDto.builder()
                .name("New name")
                .surname("New surname")
                .age(35)
                .interests("Space")
                .gender(Genders.M)
                .city(City.builder().id(31L).name("г Санкт-Петербург").build())
                .build();
        mockMvc.perform(patch("/users/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление полей на странице пользователя")
    void updatePage_InValidAge_ThrowException() throws Exception {
        UserEditDto userDto = UserEditDto.builder()
                .age(-35)
                .build();
        mockMvc.perform(patch("/users/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is("must be greater than 0")))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Удаление страницы пользователя с указанным id")
    public void deletePage_isOk() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление друга пользователю с userId")
    public void addFriend_isOk() throws Exception {
        mockMvc.perform(put("/users/2/add").param("friendId", "3"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление друга из списка друзей")
    public void deleteFriend_isOk() throws Exception {
        mockMvc.perform(put("/users/1/friends/delete").param("friendId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение списка друзей пользователя с помощью фильтра")
    public void getFriends_FriendList() throws Exception {
        mockMvc.perform(get("/users/1/friends").contentType("application_json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fio", Is.is("User2 Surname2")))
                .andExpect(jsonPath("$.content[0].age", Is.is(90)))
                .andExpect(jsonPath("$.content[0].gender", Is.is("M")))

                .andExpect(jsonPath("$.content[1].fio", Is.is("User3 Surname3")))
                .andExpect(jsonPath("$.content[1].age", Is.is(50)))
                .andExpect(jsonPath("$.content[1].gender", Is.is("M")));
    }
}
