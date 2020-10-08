package com.example.rest.repository;
import com.example.rest.entity.Order;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

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
    //Почему-то возвращает 0

    @Test
    @DisplayName("Сохранение в базе данных нового заказа")
    void insertOrder() {
        Order order = new Order("Porshe", 10000);

        Mockito.when(jdbcTemplate.update(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyInt())).thenReturn(1);
        Assertions.assertEquals(1, orderDAO.insertOrder(order));
    }

    @Test
    void getAllOrders() {
    }
}
