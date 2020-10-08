package com.example.rest.controller;

import com.example.rest.entity.Order;
import com.example.rest.service.OrderService;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

//TODO: с этой аннотацией сериализация и десериализация происходят автоматически
@RequestMapping("/api/orders")
@RestController

public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public int createOrder(@RequestBody Order order) throws SQLException {
        return orderService.createOrder(order);
    }

    @GetMapping("/")
    public List<Order> getAllOrders() throws SQLException {
        return orderService.getAllOrders();
    }
}
