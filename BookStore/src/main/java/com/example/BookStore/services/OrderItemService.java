package com.example.BookStore.services;

import com.example.BookStore.models.OrderItem;
import com.example.BookStore.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(int id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

//    public void deleteOrderItem(int id) {
//        orderItemRepository.deleteById(id);
//    }
}
