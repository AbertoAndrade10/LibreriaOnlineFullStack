package com.porfolio.books_microservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase se usa para que un administrador pueda manipular los libros (CRUD)
 * teniendo en cuanta que el ID y el ISBN se generan automaticamente al crear el
 * objeto
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationDTO {

    @NotBlank(message = "The title is obligatory")
    private String title;

    @NotBlank(message = "The author is obligatory")
    private String author;

    @NotBlank(message = "The literary genre is obligatory")
    private String literaryGenre;

    @NotBlank(message = "The price is obligatory")
    private Double price;

    @NotBlank(message = "The stock must be a positive number or zero")
    private Integer stock;

    @NotBlank(message = "The available is obligatory")
    private boolean available;

    @NotBlank(message = "The URL of image is obligatory")
    private String imageUrl;

    @NotBlank(message = "The description is obligatory")
    private String description;

    public String generateIsbn() {
        return "ISBN - " + java.util.UUID.randomUUID().toString().substring(0, 10);
    }
}
