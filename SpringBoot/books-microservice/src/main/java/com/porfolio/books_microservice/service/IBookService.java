package com.porfolio.books_microservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.exception.BookNotFoundException;

public interface IBookService {

    BookDTO createBook(BookCreationDTO bookCreationDTO);

    Page<BookDTO> getAllBooks(Pageable pageable);

    BookDTO getBookById(String id) throws BookNotFoundException;

    BookDTO updateBook(String id, BookCreationDTO bookCreationDTO) throws BookNotFoundException;

    BookDTO patchBook(String id, BookCreationDTO bookCreationDTO) throws BookNotFoundException;

    void deleteBook(String id) throws BookNotFoundException;

    List<BookDTO> searchBooksByTitle(String title);

    List<BookDTO> searchBooksByLiteraryGenre(String literaryGenre);

    List<BookDTO> getAvailableBooks();

    List<BookDTO> searchBooksByStockGreaterThan(Integer stock);

    List<BookDTO> searchBooksByPriceBetween(Double minPrice, Double maxPrice);

    List<BookDTO> searchBooksByAuthor(String author);

    BookDTO searchBookByIsbn(String isbn) throws BookNotFoundException;

    BookDTO updateImage(String id, String newImageUrl);

    List<BookDTO> searchBooksByDescription(String description);
}
