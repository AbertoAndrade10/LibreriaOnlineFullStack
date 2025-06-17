package com.porfolio.books_microservice.utils;

import org.springframework.stereotype.Component;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.model.Book;

@Component

public class MapperBooks {

    /** BookCreationDTO to Book */
    public Book bookCreationDTOtoBook(BookCreationDTO bookCreationDTO) {
        String generatedIsbn = "ISBN - " + java.util.UUID.randomUUID().toString().substring(0, 10);

        return Book.builder()
                .isbn(generatedIsbn) 
                .title(bookCreationDTO.getTitle())
                .author(bookCreationDTO.getAuthor())
                .literaryGenre(bookCreationDTO.getLiteraryGenre())
                .price(bookCreationDTO.getPrice())
                .stock(bookCreationDTO.getStock())
                .available(bookCreationDTO.isAvailable())
                .imageUrl(bookCreationDTO.getImageUrl())
                .description(bookCreationDTO.getDescription())
                .build();
    }

    /** Book to BookDTO */
    public BookDTO bookToBookDTO(Book book) {
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .literaryGenre(book.getLiteraryGenre())
                .price(book.getPrice())
                .available(book.isAvailable())
                .imageUrl(book.getImageUrl())
                .description(book.getDescription())
                .build();
    }

}
