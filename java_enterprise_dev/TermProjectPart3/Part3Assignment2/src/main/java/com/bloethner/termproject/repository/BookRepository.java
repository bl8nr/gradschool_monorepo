package com.bloethner.termproject.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.bloethner.termproject.entities.Book;

/**
 * create book repository for pagination and sorting
 */

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
