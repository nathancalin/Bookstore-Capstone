package com.example.BookStore.repositories;

import com.example.BookStore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByCategoryId(int categoryId);
}
