package com.example.BookStore.repositories;

import com.example.BookStore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    void deleteByShoppingCartId(int cartId);

    CartItem findByShoppingCartIdAndBookId(int cartId, int bookId); // Get item by cart & book
}

