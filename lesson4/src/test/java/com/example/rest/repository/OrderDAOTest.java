package com.example.rest.repository;
import com.example.rest.entity.Order;
import com.example.rest.utils.OrderRowMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    KeyHolder keyHolder;

    @Mock
    NamedParameterJdbcTemplate namedTemplate;

    @Mock
    CustomerDAO customerDAO;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderDAO = new OrderDAO(jdbcTemplate);
        orderDAO.setCustomerDAO(customerDAO);
        namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    //TODO непонятно как протестировать, если в методе у нас изменяется состояние
    // объекта KeyHolder

    @Test
    @DisplayName("Сохранение в базе данных нового заказа")
    void insertOrder() {
        Order order = new Order("Porshe", 10000);

        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1);
        Mockito.when(keyHolder.getKey()).thenReturn(1);
        order.setId(1);
        order.setCustomerId(1);
        Assertions.assertEquals(order, orderDAO.insertOrder(order));
    }


    @Test
    void getAllOrders_ShouldReturn_EmptyList() {
        Mockito.when(jdbcTemplate
                .query(Mockito.anyString(), Mockito.any(OrderRowMapper.class)))
                .thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderDAO.getAllOrders());
    }

}
