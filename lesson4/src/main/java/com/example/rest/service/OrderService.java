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

    /**
     * Проверяет, что параметр цена / price больше 0
     * В случае успеха сохраняет заказ в базе данных
     *
     * @param order
     * @return
     * @throws Exception
     */

    public Order createOrder(Order order) throws Exception {
        if(order.getPrice() > 0) {
            return orderDAO.insertOrder(order);
        }
        else throw new Exception("Стоимость должна быть больше 0");
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
}
