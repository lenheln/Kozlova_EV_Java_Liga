package com.example.rest.repository;

import com.example.rest.domain.Order;
import com.example.rest.utils.KeyHolderFactory;
import com.example.rest.utils.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String
            INSERT = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID) VALUES (:name, :price, :customer_id)";

    private static final String
            SELECT = "SELECT * FROM Orders";

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    KeyHolderFactory keyHolderFactory;

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Сохранение заказа в базе данных
     *
     * @param order /заказ
     * @return order /заказ с указанным id заказа и id клиента, выполнившего заказ
     */

    public Order insertOrder(Order order){
        Integer currentCustomerId = customerDAO.getCurrentCustomerId();
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        sqlParameterSource.addValue("name", order.getName());
        sqlParameterSource.addValue("price",order.getPrice());
        sqlParameterSource.addValue("customer_id", currentCustomerId);
        namedTemplate.update(INSERT, sqlParameterSource, keyHolder);
        Integer orderId = (Integer) keyHolder.getKey();
        order.setId(orderId);
        order.setCustomerId(currentCustomerId);
        return order;
    }

    /**
     * Возвращает список всех заказов из базы данных
     * @return список заказов
     */
    public List<Order> getAllOrders(){
        List<Order> orderList = jdbcTemplate.query(SELECT, new OrderRowMapper());
        return orderList;
    }

    public void setKeyHolderFactory(KeyHolderFactory keyHolderFactory) {
        this.keyHolderFactory = keyHolderFactory;
    }
}
