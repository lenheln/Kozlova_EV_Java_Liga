package com.example.rest.repository;
import com.example.rest.domain.Order;
import com.example.rest.utils.OrderRowMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import java.util.List;


public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Mock
    CustomerDAO customerDAO;
    @Mock
    KeyHolder keyHolder;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderDAO = new OrderDAO(jdbcTemplate, keyHolder);
        orderDAO.setCustomerDAO(customerDAO);
    }

    @Test
    @DisplayName("Сохранение в базе данных нового заказа")
    void insertOrder() {
        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1);
        Mockito.when(keyHolder.getKey()).thenReturn(1);
        Mockito.when(namedParameterJdbcTemplate.update(
                                Mockito.any(String.class),
                                Mockito.any(MapSqlParameterSource.class),
                                Mockito.any(KeyHolder.class)
                        )).thenReturn(1);

        Order expectedOrder = new Order(1, "Porshe", 10000, 1 );
        Order inputOrder = Order.builder().name("Porshe").price(10000).customerId(0).build();
        Assertions.assertEquals(expectedOrder, orderDAO.insertOrder(inputOrder));
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
