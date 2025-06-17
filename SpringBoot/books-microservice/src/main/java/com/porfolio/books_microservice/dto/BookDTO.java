package com.porfolio.books_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase se utiliza para mostrar los datos de los libros a los usuarios,
 * sin mostrar datos irrelevantes para ellos
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String literaryGenre;
    private Double price;
    private boolean available;
    private String imageUrl;
    private String description;
}
