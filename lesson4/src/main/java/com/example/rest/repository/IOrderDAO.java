package com.example.rest.repository;

import com.example.rest.entity.Order;
import java.util.List;

public interface IOrderDAO {

    public Order insertOrder(Order order);
    public List<Order> getAllOrders();
}
