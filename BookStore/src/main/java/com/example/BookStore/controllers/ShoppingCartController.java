package com.example.BookStore.controllers;

import com.example.BookStore.models.ShoppingCart;
import com.example.BookStore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/user/{userId}")
    public ShoppingCart getCartByUserId(@PathVariable int userId) {
        return shoppingCartService.getShoppingCartByUserId(userId);
    }

//    @DeleteMapping("/clear/{userId}")
//    public void clearCart(@PathVariable int userId) {
//        shoppingCartService.clearCart(userId);
//    }
}
