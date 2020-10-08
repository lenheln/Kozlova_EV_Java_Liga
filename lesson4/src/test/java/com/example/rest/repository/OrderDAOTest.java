package com.example.rest.repository;
import com.example.rest.entity.Order;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Mock
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderDAO = new OrderDAO(jdbcTemplate);
    }

    //TODO Он же должен возвращать id. Мы это не проверяем

//    @Test
//    @DisplayName("Сохранение в базе данных нового заказа")
//    void insertOrder() {
//        Order order = new Order("Porshe", 10000);
//
//        Mockito.when(jdbcTemplate.update(
//                Mockito.anyString(),
//                Mockito.anyString(),
//                Mockito.anyInt(),
//                Mockito.anyInt())).thenReturn(1);
//        Mockito.when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
//        Mockito.when(dataSource.getConnection()).thenReturn(connection);
//        Assertions.assertEquals(1, orderDAO.insertOrder(order));
//    }

//    @Test
//    void insertOrder_Shall_Return_1() {
//        Order order = new Order("Porshe", 10000);
//        int id = orderDAO.insertOrder(order);
//        Assertions.assertEquals(1, id);
//    }

    @Test
    void getAllOrders() {
    }
}
