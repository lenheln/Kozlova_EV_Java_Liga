package com.example.rest.controller;

import com.example.rest.domain.Order;
import com.example.rest.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

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
        Order order = Order.builder().name("car").price(100).customerId(1).build();
        Mockito.when(orderService.createOrder(order)).thenReturn(order);
        Assertions.assertEquals(order, orderController.createOrder(order));

        Mockito.verify(orderService, Mockito.times(1)).createOrder(Mockito.any(Order.class));
        Mockito.verifyNoMoreInteractions(orderService);
    }

    @Test
    @DisplayName("Список всех заказов")
    void getAllOrders_ShouldReturn_EntyList() {
        Mockito.when(orderService.getAllOrders()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderController.getAllOrders());

        Mockito.verify(orderService, Mockito.times(1)).getAllOrders();
        Mockito.verifyNoMoreInteractions(orderService);
    }
}