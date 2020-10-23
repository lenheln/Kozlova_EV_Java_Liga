package com.example.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.rest.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 *  Интеграционные тесты для контроллера
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Создание нового заказа")
    void createOrder_Should_Return_Json() throws Exception {
        Order order = Order.builder().name("car").price(100).build();
        mockMvc.perform(post("/api/v1/order/createOrder")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"car\",\"price\":100,\"customerId\":1}"));
    }

    @Test
    @DisplayName("Получение списка всех заказов")
    void getAllOrders_Should_Return_JSON_array_withOneOrder() throws Exception {
        mockMvc.perform(get("/api/v1/order/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"car\",\"price\":100,\"customerId\":1}]"));
    }
}