package com.example.social_network.controller;

import com.example.social_network.domain.City;
import com.example.social_network.dto.UserEditDto;
import com.example.social_network.dto.UserRegisterDto;
import com.example.social_network.utils.Genders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
                .city(new City(1, "Москва"))
                .interests("Programming")
                .build();

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Получение пользователей с применением фильтра по всем полям")
    public void getUsers_WithFilter_ReturnUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users").contentType("application/json")
                .param("page", "0")
                .param("size", "3")
                .param("name", "User1")
                .param("surname", "Surname1")
                .param("age", "33")
                .param("gender", "F")
                .param("city", "Москва")
                .param("interests", "books"))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        String expected = "{\"content\":[{\"fio\":\"User1 Surname1\",\"gender\":\"F\",\"age\":33}]," +
                "\"pageable\":{\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true}," +
                "\"offset\":0,\"pageNumber\":0,\"pageSize\":3,\"unpaged\":false,\"paged\":true}," +
                "\"totalPages\":1,\"last\":true,\"totalElements\":1,\"size\":3,\"number\":0," +
                "\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true}," +
                "\"numberOfElements\":1,\"first\":true,\"empty\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    @DisplayName("Получение пользователей без фильтра. Пагинация default")
    public void getUsers_ReturnUsersList() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users").contentType("application/json"))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{\"content\":[{\"fio\":\"User1 Surname1\",\"gender\":\"F\",\"age\":33}," +
                "{\"fio\":\"User2 Surname2\",\"gender\":\"M\",\"age\":90}," +
                "{\"fio\":\"User3 Surname3\",\"gender\":\"M\",\"age\":50}]," +
                "\"pageable\":{\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true}," +
                "\"offset\":0,\"pageSize\":3,\"pageNumber\":0,\"paged\":true,\"unpaged\":false}," +
                "\"totalElements\":3,\"totalPages\":1,\"last\":true,\"number\":0," +
                "\"size\":3,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true}," +
                "\"numberOfElements\":3,\"first\":true,\"empty\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    @DisplayName("Получение страницы пользователя по его id")
    public void getPage_id1_PageUser1() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"fio\":\"User1 Surname1\"," +
                        "\"age\":33," +
                        "\"gender\":\"F\"," +
                        "\"interests\":\"books\"," +
                        "\"city\":{\"id\":1,\"name\":\"Москва\"}}"));
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
                .city(new City(2, "Петербург"))
                .build();
        mockMvc.perform(patch("/users/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNoContent());
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
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление друга из списка друзей")
    public void deleteFriend_isOk() throws Exception {
        mockMvc.perform(put("/users/1/friends/delete").param("friendId", "2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Получение списка друзей пользователя с помощью фильтра")
    public void getFriends_FriendList() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/users/1/friends").contentType("application_json"))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        String expected = "{\"content\":[{\"fio\":\"User2 Surname2\",\"gender\":\"M\",\"age\":90}," +
                "{\"fio\":\"User3 Surname3\",\"gender\":\"M\",\"age\":50}]," +
                "\"pageable\":{\"sort\":{\"unsorted\":true,\"sorted\":false,\"empty\":true}," +
                "\"offset\":0,\"pageNumber\":0,\"pageSize\":3,\"paged\":true,\"unpaged\":false}," +
                "\"totalElements\":2,\"totalPages\":1,\"last\":true,\"number\":0,\"size\":3," +
                "\"sort\":{\"unsorted\":true,\"sorted\":false,\"empty\":true}," +
                "\"numberOfElements\":2,\"first\":true,\"empty\":false}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}
