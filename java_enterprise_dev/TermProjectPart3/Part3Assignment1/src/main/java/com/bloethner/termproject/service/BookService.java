package com.bloethner.termproject.service;

import com.bloethner.termproject.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Create bookService interface contract
 */

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    Page<Book> findAllByPage(Pageable pageable);
    Book deleteById(Long bookId);
}
