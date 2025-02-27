package com.example.BookStore.services;

import com.example.BookStore.models.CartItem;
import com.example.BookStore.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.BookStore.models.ShoppingCart;
import com.example.BookStore.repositories.ShoppingCartRepository;
import com.example.BookStore.repositories.UserRepository;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public CartItem addCartItem(int userId, CartItem cartItem) {
        // Check if user has a shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId);

        if (cart == null) {
            // Create a new shopping cart for the user
            cart = new ShoppingCart();
            cart.setUser(userRepository.findById(userId).orElse(null)); // Make sure user exists
            cart = shoppingCartRepository.save(cart); // Save the new cart
        }

        cartItem.setShoppingCart(cart);

        // Check if the item already exists in the cart
        CartItem existingItem = cartItemRepository.findByShoppingCartIdAndBookId(cart.getId(), cartItem.getBook().getId());

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingItem);
        }

        return cartItemRepository.save(cartItem);
    }


    public void updateCartItemQuantity(int itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeCartItem(int itemId) {
        cartItemRepository.deleteById(itemId);
    }
}

