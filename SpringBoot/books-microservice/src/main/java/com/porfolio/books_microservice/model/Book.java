package com.porfolio.books_microservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad Book. Modelo para la base de datos de MongoDB
 */

@Document(collection = "books")
@Getter
@Setter
@Builder
public class Book {

    @Id
    private String id;

    private String isbn;
    private String title;
    private String author;
    private String literaryGenre;
    private Double price;
    private Integer stock;
    private boolean available;
    private String imageUrl;
    private String description;

    public Book() {
        this.isbn = generateIsbn();
    }

    public Book(String title, String author, String literaryGenre, Double price, Integer stock, boolean available,
            String imageUrl, String description) {
        this.title = title;
        this.author = author;
        this.literaryGenre = literaryGenre;
        this.price = price;
        this.stock = stock;
        this.available = available;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isbn = generateIsbn();
    }

    public Book(String id, String isbn, String title, String author, String literaryGenre, Double price, Integer stock,
            boolean available, String imageUrl, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.literaryGenre = literaryGenre;
        this.price = price;
        this.stock = stock;
        this.available = available;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String generateIsbn() {
        return "ISBN - " + java.util.UUID.randomUUID().toString().substring(0, 10);
    }
}