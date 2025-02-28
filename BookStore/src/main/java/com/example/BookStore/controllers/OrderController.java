package com.example.BookStore.controllers;

import com.example.BookStore.models.Order;
import com.example.BookStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    // Checkout (place an order)
    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable int userId) {
        return orderService.checkout(userId);
    }
}
