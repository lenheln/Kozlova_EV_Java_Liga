package com.example.rest.repository;

import com.example.rest.CustomDataSource;
import com.example.rest.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
//TODO: нужен ли здесь этот уровень или костыльно забьем пользователей при загрузке базы
@Component
public class CustomerDAO {
    private Connection connection = null;

    @Autowired
    public CustomerDAO(CustomDataSource customDataSource) {
        this.connection = customDataSource.getConnection();
    }

    public int saveCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (name, email) Values (?, ?)";
        PreparedStatement prepStatement = connection.prepareStatement(sql);
        prepStatement.setString(1, customer.getName());
        prepStatement.setString(2, customer.getEmail());
        prepStatement.execute();

        //TODO может тоже не нужно
        int id = getLastId();
        customer.setId(id);
        return id;
    }

    public int getLastId() throws SQLException {
        String sql = "SELECT * from Customers";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int id = 0;
        if (rs != null) {
            rs.last();
            id = rs.getRow();
        }
        return id;
    }

//    public Customer getCustomerById(int id) {
//        String sql = "SELECT * FROM Customers WHERE id = ?";
//        PreparedStatement statement = null;
//        Customer customer = null;
//        try {
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1, id);
////        statement.execute();
//            ResultSet rs = statement.executeQuery();
//            while(rs.next()){
//                id = rs.getInt("id");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                customer = new Customer(id, name, email);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        return customer;
//    }
}
