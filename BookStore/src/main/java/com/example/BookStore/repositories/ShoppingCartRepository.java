package com.example.BookStore.repositories;

import com.example.BookStore.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findByUserId(int userId); // Get cart by user ID
}
