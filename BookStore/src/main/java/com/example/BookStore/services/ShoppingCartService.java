package com.example.BookStore.services;

import com.example.BookStore.models.ShoppingCart;
import com.example.BookStore.repositories.ShoppingCartRepository;
import com.example.BookStore.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public ShoppingCart getShoppingCartByUserId(int userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

//    public void clearCart(int userId) {
//        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);
//        if (cart != null) {
//            cartItemRepository.deleteByShoppingCartId(cart.getId()); // Delete all cart items
//            cart.getCartItems().clear(); // Clear the list in memory
//            shoppingCartRepository.save(cart); // Save updated cart
//        }
//    }


}


