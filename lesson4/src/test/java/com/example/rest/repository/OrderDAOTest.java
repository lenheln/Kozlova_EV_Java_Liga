package com.example.rest.repository;
import com.example.rest.domain.Order;
import com.example.rest.utils.OrderRowMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import java.util.List;


public class OrderDAOTest {

    private OrderDAO orderDAO;

    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    CustomerDAO customerDAO;
    @Mock
    KeyHolder keyHolder;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderDAO = new OrderDAO(jdbcTemplate, keyHolder, customerDAO);
    }

    @Test
    @DisplayName("Сохранение в базе данных нового заказа")
    public void insertOrder_ShouldReturn_OrderWithId() throws Exception {
        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1);
        Mockito.when(keyHolder.getKey()).thenReturn(1);
        Mockito.when(jdbcTemplate.update(
                                Mockito.any(PreparedStatementCreator.class),
                                Mockito.any(KeyHolder.class)
                        )).thenReturn(1);

        Order expectedOrder = new Order(1, "Porshe", 10000, 1 );
        Order inputOrder = Order.builder().name("Porshe").price(10000).customerId(0).build();
        Assertions.assertEquals(expectedOrder, orderDAO.createOrder(inputOrder));

        Mockito.verify(customerDAO, Mockito.times(1)).getCurrentCustomerId();
        Mockito.verify(keyHolder, Mockito.times(1)).getKey();
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update
                (Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class));

        Mockito.verifyNoMoreInteractions(customerDAO);
        Mockito.verifyNoMoreInteractions(keyHolder);
        Mockito.verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    @DisplayName("Ошибка при сохранении заказа")
    public void insertOrder_ShouldReturn_Exception() throws Exception {
        Mockito.when(customerDAO.getCurrentCustomerId()).thenReturn(1);
        Mockito.when(keyHolder.getKey()).thenReturn(1);
        Mockito.when(jdbcTemplate.update(
                Mockito.any(PreparedStatementCreator.class),
                Mockito.any(KeyHolder.class)
        )).thenReturn(0);
        Order order = Order.builder().name("Car").price(100).customerId(1).build();
        Exception exception = Assertions.assertThrows(Exception.class, () -> orderDAO.createOrder(order));
        String expectedException = "Не удалось создать новый заказ";
        Assertions.assertEquals(expectedException, exception.getMessage());

        Mockito.verify(customerDAO, Mockito.times(1)).getCurrentCustomerId();
        Mockito.verify(keyHolder, Mockito.never()).getKey();
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update
                (Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class));

        Mockito.verifyNoMoreInteractions(customerDAO);
        Mockito.verifyNoMoreInteractions(keyHolder);
        Mockito.verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    @DisplayName("Получение списка всех заказов")
    public void getAllOrders_ShouldReturn_EmptyList() {
        Mockito.when(jdbcTemplate
                .query(Mockito.anyString(), Mockito.any(OrderRowMapper.class)))
                .thenReturn(List.of());
        Assertions.assertEquals(List.of(), orderDAO.getAllOrders());

        Mockito.verify(jdbcTemplate, Mockito.times(1)).query
                (Mockito.any(String.class), Mockito.any(RowMapper.class));
        Mockito.verifyNoMoreInteractions(jdbcTemplate);
    }
}
