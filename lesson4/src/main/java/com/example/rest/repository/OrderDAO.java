package com.example.rest.repository;

import com.example.rest.entity.Order;
import com.example.rest.utils.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAO implements IOrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerDAO customerDAO;

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Сохранение заказа в базе данных
     *
     * @param order /заказ
     * @return order /заказ с указанным id заказа и id клиента, выполнившего заказ
     */

    @Override
    public Order insertOrder(Order order){
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID) " +
                "VALUES (:name, :price, :customer_id)";
        int currentCustomerId = customerDAO.getCurrentCustomerId();
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("name", order.getName());
        sqlParameterSource.addValue("price",order.getPrice());
        sqlParameterSource.addValue("customer_id", currentCustomerId);
        namedTemplate.update(sql, sqlParameterSource, keyHolder);
//        int orderId = (int) keyHolder.getKey();
//        order.setId(orderId);
//        order.setCustomerId(currentCustomerId);
        return order;
    }

    /**
     * Возвращает список всех заказов из базы данных
     * @return список заказов
     */

    @Override
    public List<Order> getAllOrders(){
        String sql = "SELECT * FROM Orders";
        List<Order> orderList = jdbcTemplate.query(sql, new OrderRowMapper());
        return orderList;
    }
}
