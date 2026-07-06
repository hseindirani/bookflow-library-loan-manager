package com.nordea.bookflow.book;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("books")
public class Book {

    @Id
    private Long id;

    private String isbn;
    private String title;
    private String author;
    private Integer totalCopies;
    private Integer availableCopies;

    public Book() {
    }

    public Book(Long id, String isbn, String title, String author, Integer totalCopies, Integer availableCopies) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void decreaseAvailableCopies() {
        this.availableCopies--;
    }

    public void increaseAvailableCopies() {
        this.availableCopies++;
    }
}