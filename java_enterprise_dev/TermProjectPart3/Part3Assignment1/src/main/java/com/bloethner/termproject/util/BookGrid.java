package com.bloethner.termproject.util;

import com.bloethner.termproject.entities.Book;
import java.util.List;

public class BookGrid {

    /**
     * Store data regarding
     * total pages
     * current page
     * totals records
     * bookData (current book viewed data)
     */
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Book> bookData;

    /**
     * Getters
     */
    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public List<Book> getBookData() {
        return bookData;
    }

    /**
     * Setters
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public void setBookData(List<Book> bookData) {
        this.bookData = bookData;
    }
}