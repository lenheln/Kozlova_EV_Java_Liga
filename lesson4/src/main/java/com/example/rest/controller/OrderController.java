package com.example.rest.controller;

import com.example.rest.domain.Order;
import com.example.rest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Контроллер для работы с сущностью Order / Продукт
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody Order order) throws Exception {
        return orderService.createOrder(order);
    }

    @GetMapping("/")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
