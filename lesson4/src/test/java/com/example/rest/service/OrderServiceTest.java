package com.example.rest.service;

import com.example.rest.domain.Order;
import com.example.rest.repository.OrderDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    private OrderService orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.orderService = new OrderService(orderDAO);
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder_Should_Return_Order() throws Exception {
        Order order = Order.builder().name("Porsche").price(10000).customerId(1).build();
        Mockito.when(orderDAO.createOrder(order)).thenReturn(order);
        Assertions.assertEquals(order, orderService.createOrder(order));

        Mockito.verify(orderDAO, Mockito.times(1)).createOrder(Mockito.any(Order.class));
        Mockito.verifyNoMoreInteractions(orderDAO);
    }

    @Test
    @DisplayName("Создание заказа с отрицательной ценой вызывает ошибку")
    public void createOrder_Should_Throw_Exception() throws Exception {
        Order order = Order.builder().name("Porsche").price(-10000).customerId(1).build();
        Mockito.when(orderDAO.createOrder(order)).thenReturn(order);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            orderService.createOrder(order);
        });

        String expectedMessage = "Стоимость должна быть больше 0";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        Mockito.verify(orderDAO, Mockito.never()).createOrder(Mockito.any(Order.class));
        Mockito.verifyNoMoreInteractions(orderDAO);
    }

    @Test
    @DisplayName("Список всех заказов")
    public void getAllOrders_Should_Return_EmptyList(){
        Mockito.when(orderDAO.getAllOrders()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderService.getAllOrders());

        Mockito.verify(orderDAO, Mockito.times(1)).getAllOrders();
        Mockito.verifyNoMoreInteractions(orderDAO);
    }

}
