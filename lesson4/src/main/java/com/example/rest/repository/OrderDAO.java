package com.example.rest.repository;

import com.example.rest.domain.Order;
import com.example.rest.utils.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDAO {

    private static final String
            INSERT = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID) VALUES (? , ? , ?)";

    private static final String
            SELECT = "SELECT * FROM Orders";

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder;
    private final CustomerDAO customerDAO;

    /**
     * Сохранение заказа в базе данных
     *
     * @param order /заказ
     * @return order /заказ с указанным id заказа и id клиента, выполнившего заказ
     */

    public Order createOrder(Order order) throws Exception {
        Integer currentCustomerId = customerDAO.getCurrentCustomerId();
        int resultSet = jdbcTemplate.update(
                connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getName());
            ps.setInt(2, order.getPrice());
            ps.setInt(3, currentCustomerId);
            return ps;
        }, keyHolder);

        if(resultSet == 0){
            log.error("Couldn't save order by user #{}", currentCustomerId);
            throw new Exception("Не удалось создать новый заказ");
        } else {
            Integer orderId = keyHolder.getKey().intValue();
            order.setId(orderId);
            order.setCustomerId(currentCustomerId);
            log.info("Order #{} has saved successfully", orderId);
            return order;
        }
    }

    /**
     * Возвращает список всех заказов из базы данных
     * @return список заказов
     */
    public List<Order> getAllOrders(){
        List<Order> orderList = jdbcTemplate.query(SELECT, new OrderRowMapper());
        return orderList;
    }
}
