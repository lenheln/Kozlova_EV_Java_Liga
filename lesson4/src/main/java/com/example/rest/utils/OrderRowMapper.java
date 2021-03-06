package com.example.rest.utils;

import com.example.rest.domain.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Сериализация данных из базы данных в объект
 */
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("ID"));
        order.setName(rs.getString("NAME"));
        order.setPrice(rs.getInt("PRICE"));
        order.setCustomerId(rs.getInt("CUSTOMER_ID"));
        return order;
    }
}
