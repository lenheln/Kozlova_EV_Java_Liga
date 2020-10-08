package com.example.rest;

import com.example.rest.repository.OrderDAO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class CustomDataSource implements ApplicationRunner {

    private Connection connection = null;
//    private OrderDAO orderDAO;

//    public CustomDataSource() {
//        //Заменить на try-with-resources
//        try {
//            Class.forName ("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection("jdbc:h2:~/test", "", "");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Class.forName ("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:~/test", "", "");

        //TODO: creare table Customers
//        String sql = "DROP TABLE IF EXISTS Customers";
//        Statement statement = connection.createStatement();
//        statement.execute(sql);
        String sql = "CREATE TABLE IF NOT EXISTS Customers(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255), EMAIL VARCHAR(255))";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        //TODO можно ли так забить ? или по-другому сделать?
        //TODO не создался юзер из бд
        //TDOD тут ничего не должно заполняться и уровень DAO имплементировать не нужно
//        sql = "INSERT INTO Customers (name, email) Values ('BOB', 'bob@mail.ru')";
//        statement = connection.createStatement();
//        statement.execute(sql);
//        sql = "INSERT INTO Customers (name, email) Values ('Max', 'max@gmail.com')";
//        statement = connection.createStatement();
//        statement.execute(sql);

        //TODO: create table Orders. А надо ли удалять
        sql = "DROP TABLE IF EXISTS Orders";
        statement = connection.createStatement();
        statement.execute(sql);

        //TODO добавить колонку customer
        sql = "CREATE TABLE IF NOT EXISTS Orders(" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(255), " +
                "PRICE INT, " +
                "CUSTOMER_ID INT)";

        statement = connection.createStatement();
        statement.executeUpdate(sql);

        sql = " ALTER TABLE Orders ADD FOREIGN KEY (CUSTOMER_ID) REFERENCES Customers(id)";
        statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public Connection getConnection() {
        return connection;
    }
}
