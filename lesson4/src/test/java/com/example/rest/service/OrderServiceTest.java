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
import org.springframework.http.ResponseEntity;

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
        Order order = new Order("Porshe", 10000);
        Mockito.when(orderDAO.insertOrder(order)).thenReturn(order);
        Assertions.assertEquals(order, orderService.createOrder(order));
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder_Should_Throw_Exception() throws Exception {
        Order order = new Order("Porshe", -100);
        Mockito.when(orderDAO.insertOrder(order)).thenReturn(order);
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            orderService.createOrder(order);
        });

        String expectedMessage = "Стоимость должна быть больше 0";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Список всех заказов")
    public void getAllOrders_Should_Return_EmptyList(){
        Mockito.when(orderDAO.getAllOrders()).thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderService.getAllOrders());
    }

}
