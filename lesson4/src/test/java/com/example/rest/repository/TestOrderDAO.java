package com.example.rest.repository;

import com.example.rest.CustomDataSource;
import com.example.rest.entity.Order;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

//TODO Класс ApplicationRunner запускается только после того, как запуститься SpringRun.
// Если запускать тест отдельно, то база не очищается
// Надо здесь как-то чистить базу
// Rollback почему то не делается

public class TestOrderDAO {

    private CustomDataSource customDataSource;
    private OrderDAO orderDAO;

    // TODO По идее тут нужно инициализировать новую базу и подключение к ней

    @BeforeEach
    public void initDataBase(){
//        try {
//            Class.forName ("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:h2:~/test", "", "");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        try {
////            String sql = "DROP TABLE IF EXISTS Customers";
////            Statement statement = null;
////            statement = connection.createStatement();
////            statement.execute(sql);
//            String sql = "CREATE TABLE IF NOT EXISTS Customers(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), EMAIL VARCHAR(255))";
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        try {
//            String sql = "CREATE TABLE IF NOT EXISTS Orders(" +
//                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
//                    "NAME VARCHAR(255), " +
//                    "PRICE INT, " +
//                    "CUSTOMER_ID INT)";
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(sql);
//
//            sql = " ALTER TABLE Orders ADD FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(id)";
//            statement = connection.createStatement();
//            statement.executeUpdate(sql);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        orderDAO = new OrderDAO(connection);
    }

    //TODO Он же должен возвращать id. Мы это не проверяем

    @Test
    @DisplayName("Сохранение в базе нового заказа")
    @Rollback(true)
    public void saveOrder(){
        Order order = new Order();
        order.setName("Porshe");
        order.setPrice(1000000);
        orderDAO.saveOrder(order);

        List<Order> orderList = orderDAO.getAllOrders();
        Assertions.assertEquals(order.getName(), orderList.get(0).getName());
    }

    @Test
    @Rollback(true)
    public void saveOrder2(){
        Order order = new Order();
        order.setName("Honda");
        order.setPrice(500000);
        orderDAO.saveOrder(order);

        List<Order> orderList = orderDAO.getAllOrders();
        Assertions.assertEquals(order.getName(), orderList.get(0).getName());
    }
}
