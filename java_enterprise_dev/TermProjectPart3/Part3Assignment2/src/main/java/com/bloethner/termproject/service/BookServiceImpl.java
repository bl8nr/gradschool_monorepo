package com.bloethner.termproject.service;

import com.bloethner.termproject.entities.Book;
import com.bloethner.termproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.google.common.collect.Lists;

@Service("springJpaBookService")
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * return all books (readonly)
     */
    @Override
    @Transactional(readOnly=true)
    public List<Book> findAll() {
        return Lists.newArrayList(bookRepository.findAll());
    }

    /**
     * return all books (readonly)
     * according to pageable parameters
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAllByPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * return one book by book id (readonly)
     */
    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id).get();
    }

    /**
     * save a book via book object
     * of update a book via book object
     */
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    /**
     * delete a book via book id
     * return book for convenience
     */
    @Override
    public Book deleteById(Long bookId) {
        Book deletedBook = this.findById(bookId);
        bookRepository.delete(deletedBook);

        return deletedBook;
    }

}
