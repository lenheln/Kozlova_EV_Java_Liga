package com.example.rest.service;

import com.example.rest.entity.Order;
import com.example.rest.repository.OrderDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    public void createOrder_Should_Return_OrderId(){

        Order order = new Order("Porshe", 10000);
        Mockito.when(orderDAO.insertOrder(order)).thenReturn(2);
        Assertions.assertEquals(2, orderService.createOrder(order));
    }
}
