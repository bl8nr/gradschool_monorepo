package com.bloethner.termproject.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import java.io.Serializable;
import static javax.persistence.GenerationType.IDENTITY;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    /**
     * Configure ID column/param
     * uses non-updatable generated value
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    /**
     * Configure category column/param and validations
     */
    @NotBlank(message="{validation.category.NotBlank.message}")
    @Size(min=1, max=100, message="{validation.category.Size.message}")
    @Column(name = "CATEGORY")
    private String category;

    /**
     * Configure isbn column/param and validations
     */
    @NotBlank(message="{validation.isbn.NotBlank.message}")
    @Size(min=1, max=10, message="{validation.isbn.Size.message}")
    @Column(name = "ISBN")
    private String isbn;

    /**
     * Configure title column/param and validations
     */
    @NotBlank(message="{validation.title.NotBlank.message}")
    @Size(min=2, max=200, message="{validation.title.Size.message}")
    @Column(name = "TITLE")
    private String title;

    /**
     * Configure price column/param and validations
     */
    @NotNull(message="{validation.price.NotNull.message}")
    @Column(name = "PRICE")
    private Float price;

    /**
     * configure publisher column/param and validations
     */
    @Column(name = "PUBLISHER")
    private String publisher;

    /**
     * getters */
    public Long getId() { return this.id; }
    public String getCategory() { return this.category; }
    public String getIsbn() { return this.isbn; }
    public String getTitle() { return this.title; }
    public Float getPrice() { return this.price; }
    public String getPublisher() { return this.publisher; };

    /**
     * setters */
    public void setId(Long id) { this.id = id; }
    public void setCategory(String category) { this.category = category; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(Float price) { this.price = price; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
}
