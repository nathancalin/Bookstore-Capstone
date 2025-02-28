package com.example.BookStore.services;

import com.example.BookStore.models.*;
import com.example.BookStore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Checkout Process: Transfer cart items to order items
    @Transactional
    public Order checkout(int userId) {
        // Get user's cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty or does not exist.");
        }

        // Create and save a new order first
        Order order = new Order();
        order.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        order.setOrderDate(new Date());
        order.setTotalAmount(0.0); // Initialize total amount
        order = orderRepository.save(order); // Save order first

        double totalAmount = 0.0;

        // Convert cart items to order items
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Associate with saved order
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice() * cartItem.getQuantity());

            totalAmount += orderItem.getPrice();
            orderItemRepository.save(orderItem);
        }

        // Update the order total amount
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // Delete all cart items and save the cart
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);

        return order;
    }
}
