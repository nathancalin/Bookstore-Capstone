package com.example.BookStore.controllers;

import com.example.BookStore.models.CartItem;
import com.example.BookStore.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add/{userId}")
    public CartItem addCartItem(@PathVariable int userId, @RequestBody CartItem cartItem) {
        return cartItemService.addCartItem(userId, cartItem);
    }

    @PutMapping("/update/{itemId}")
    public void updateCartItem(@PathVariable int itemId, @RequestParam int quantity) {
        cartItemService.updateCartItemQuantity(itemId, quantity);
    }

    @DeleteMapping("/remove/{itemId}")
    public void removeCartItem(@PathVariable int itemId) {
        cartItemService.removeCartItem(itemId);
    }
}
