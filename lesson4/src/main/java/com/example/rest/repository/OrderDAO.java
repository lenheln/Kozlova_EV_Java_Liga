package com.example.rest.repository;

import com.example.rest.CustomDataSource;
import com.example.rest.entity.Customer;
import com.example.rest.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: как соединить LoadDB и DAO уровень
@Component
public class OrderDAO {

    private Connection connection = null;

    @Autowired
    private CustomerDAO customerDAO;


    @Autowired
    public OrderDAO(CustomDataSource customDataSource) {
        this.connection = customDataSource.getConnection();
    }

    public OrderDAO(Connection connection){
        this.connection = connection;
    }

    public OrderDAO() {
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    //TODO дописать
    /*
        Сохраняет заказ в базе данных

        @param Order
     */
    public int saveOrder(Order order) {
        String sql = "INSERT INTO Orders (NAME, PRICE, CUSTOMER_ID ) Values (?, ?, ?)";
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, order.getName());
            prepStatement.setInt(2, order.getPrice());

            //Customer currentCustomer = customerDAO.getCustomerById(1);
            //TODO просто поставили 1 вместо getCustomerById
            prepStatement.setInt(3, 1);
            prepStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }

        //TODO а зачем мы вообще это делаем. Может в Order не нужно хранить id
        int id = getLastId();
        order.setId(id);
        return id;
    }

    public int getLastId() {
        //TODO: Тут криво, хотя h2 возможно и не дает по другому
        //TODO вообще в отдельную функцию это надо вынести
        String sql = "SELECT * from Orders";
        Statement statement = null;
        int id = 0;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                rs.last();
                id = rs.getRow();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }


    public List<Order> getAllOrders() {
        String sql = "select * from Orders";
        Statement statement = null;
        List<Order> orderList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Order order = new Order(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("customer_id"));
                orderList.add(order);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orderList;
    }
}
