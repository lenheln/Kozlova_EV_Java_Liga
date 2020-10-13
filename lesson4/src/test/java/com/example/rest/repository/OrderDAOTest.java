package com.example.rest.repository;
import com.example.rest.entity.Order;
import com.example.rest.utils.KeyHolderFactory;
import com.example.rest.utils.OrderRowMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import static org.mockito.Mockito.mock;

public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Mock
    CustomerDAO customerDAO;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderDAO = new OrderDAO(jdbcTemplate);
        orderDAO.setCustomerDAO(customerDAO);
    }

    /*
        TODO непонятно как написать тест, если в методе insertOrder
         объект KeyHolder объявляется, а затем меняет состояние
     */

    @Test
    @DisplayName("Сохранение в базе данных нового заказа")
    void insertOrder() {
        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1);
        KeyHolder keyHolderMock = Mockito.mock(KeyHolder.class);
        KeyHolderFactory keyHolderFactory = Mockito.mock(KeyHolderFactory.class);
        Mockito.when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolderMock);
        Mockito.when(keyHolderMock.getKey()).thenReturn(1);

        orderDAO.setKeyHolderFactory(keyHolderFactory);

        Mockito.when(namedParameterJdbcTemplate.update(
                                Mockito.any(String.class),
                                Mockito.any(MapSqlParameterSource.class),
                                Mockito.any(KeyHolder.class)
                        )).thenReturn(1);

        Order orderExpected = new Order(1, "Porshe", 10000, 1 );
        Order orderInput = new Order("Porshe", 10000 );
        Assertions.assertEquals(orderExpected, orderDAO.insertOrder(orderInput));
    }

    @Test
    @DisplayName("Список всех заказов")
    void getAllOrders_ShouldReturn_EmptyList() {
        Mockito.when(jdbcTemplate
                .query(Mockito.anyString(), Mockito.any(OrderRowMapper.class)))
                .thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderDAO.getAllOrders());
    }

}
