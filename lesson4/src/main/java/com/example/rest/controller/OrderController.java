package com.example.rest.controller;

import com.example.rest.entity.Order;
import com.example.rest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
    Контроллер для работы с сущностью Order / Продукт
 */

@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public int createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
