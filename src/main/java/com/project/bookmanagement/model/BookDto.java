package com.project.bookmanagement.model;

import com.project.bookmanagement.repo.Book;

public class BookDto {
    private Long id;
    private String title;
    private String author;
    private double price;

    // Default constructor
    public BookDto() {
        this(null, null, null, 0.0);
    }

    // Constructor with all fields
    public BookDto(Long id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Constructor with Book entity
    public BookDto(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice().doubleValue());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
