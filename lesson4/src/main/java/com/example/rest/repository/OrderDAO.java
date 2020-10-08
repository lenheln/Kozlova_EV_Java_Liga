package com.example.rest.repository;

import com.example.rest.entity.Order;
import com.example.rest.utils.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDAO implements IOrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerDAO customerDAO;

    /**
     * Сохранение заказа в базе данных
     *
     * @param order заказ
     * @return id заказа
     */

    //TODO надо украсить как-то эту функцию, а то совсем страшно
    @Override
    public Order insertOrder(Order order){
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID ) Values (?, ?, ?)";
//        jdbcTemplate.update(sql, order.getName(), order.getPrice(), customerDAO.getCurrentCustomerId());
//        int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
//        return id;


        int id = 0;
        int currentCustomerId = customerDAO.getCurrentCustomerId();
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getName());
            statement.setInt(2, order.getPrice());
            statement.setInt(3, currentCustomerId);
            statement.executeUpdate();

            order.setCustomerId(currentCustomerId);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                order.setId(id);
            }
            else {
                throw new SQLException("Creating order failed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
