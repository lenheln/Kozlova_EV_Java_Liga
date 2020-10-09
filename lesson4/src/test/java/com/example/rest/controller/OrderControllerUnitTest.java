package com.example.rest.controller;

import com.example.rest.entity.Order;
import com.example.rest.repository.OrderDAO;
import com.example.rest.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Unit - тесты для контроллера
 */

class OrderControllerUnitTest {

    private OrderController orderController;

    @Mock
    OrderService orderService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderController = new OrderController(orderService);
    }

    @Test
    @DisplayName("Создание заказа")
    void createOrder_ShouldReturn_InputOrder() throws Exception {
        Order order = new Order("car", 100);
        Mockito.when(orderService.createOrder(order)).thenReturn(order);
        Assertions.assertEquals(order, orderController.createOrder(order));
    }

    @Test
    @DisplayName("Список всех заказов")
    void getAllOrders_ShouldReturn_EntyList() {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderController.getAllOrders());

    }
}