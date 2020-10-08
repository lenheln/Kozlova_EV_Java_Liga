package com.example.rest.service;

import com.example.rest.entity.Order;
import com.example.rest.repository.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;

    //TODO: судя по диаграмме сервис должен как-то проверять валидность процедуры
    public int createOrder(Order order) {
        return orderDAO.insertOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
}
