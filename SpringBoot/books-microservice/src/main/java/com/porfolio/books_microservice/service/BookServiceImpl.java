package com.porfolio.books_microservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.exception.BookNotFoundException;
import com.porfolio.books_microservice.model.Book;
import com.porfolio.books_microservice.repository.BookRepository;
import com.porfolio.books_microservice.utils.MapperBooks;
import com.porfolio.books_microservice.utils.Utils;

@Service
public class BookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final MapperBooks mapperBooks;

    public BookServiceImpl(BookRepository bookRepository, MapperBooks mapperBooks) {
        this.bookRepository = bookRepository;
        this.mapperBooks = mapperBooks;
    }

    /** CREATE Book */
    @Override
    public BookDTO createBook(BookCreationDTO bookCreationDTO) {
        Book book = mapperBooks.bookCreationDTOtoBook(bookCreationDTO);
        Book savedBook = bookRepository.save(book);
        return mapperBooks.bookToBookDTO(savedBook);
    }

    /** GET all books (pageable) */
    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(mapperBooks::bookToBookDTO);
    }

    /** GET book by id */
    @Override
    public BookDTO getBookById(String id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");

        }
        return mapperBooks.bookToBookDTO(bookOptional.get());
    }

    /** UPDATE book by id and BookCreationDTO */
    @Override
    public BookDTO updateBook(String id, BookCreationDTO bookCreationDTO) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        Book book = bookOptional.get();
        // Full update
        book.setTitle(bookCreationDTO.getTitle());
        book.setAuthor(bookCreationDTO.getAuthor());
        book.setLiteraryGenre(bookCreationDTO.getLiteraryGenre());
        book.setPrice(bookCreationDTO.getPrice());
        book.setStock(bookCreationDTO.getStock());
        book.setAvailable(bookCreationDTO.isAvailable());
        book.setImageUrl(bookCreationDTO.getImageUrl());
        book.setDescription(bookCreationDTO.getDescription());
        Book updatedBook = bookRepository.save(book);
        return mapperBooks.bookToBookDTO(updatedBook);
    }

    /** PATCH Book by id and BookCreationDTO */
    @Override
    public BookDTO patchBook(String id, BookCreationDTO bookCreationDTO) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        Book book = bookOptional.get();
        Utils.updateFieldIfPresent(bookCreationDTO.getTitle(), book::setTitle);
        Utils.updateFieldIfPresent(bookCreationDTO.getAuthor(), book::setAuthor);
        Utils.updateFieldIfPresent(bookCreationDTO.getLiteraryGenre(), book::setLiteraryGenre);
        Utils.updateFieldIfPresent(bookCreationDTO.getPrice(), book::setPrice);
        Utils.updateFieldIfPresent(bookCreationDTO.getStock(), book::setStock);
        Utils.updateFieldIfPresent(bookCreationDTO.isAvailable(), book::setAvailable);
        Utils.updateFieldIfPresent(bookCreationDTO.getImageUrl(), book::setImageUrl);
        Utils.updateFieldIfPresent(bookCreationDTO.getDescription(), book::setDescription);

        Book updatedBook = bookRepository.save(book);
        return mapperBooks.bookToBookDTO(updatedBook);
    }

    /** DELETE Book by ID */
    @Override
    public void deleteBook(String id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.softDeleteById(id); // Soft delete
    }

    /** GET BookDTO by Title */
    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET BookDTO by literary genre */
    @Override
    public List<BookDTO> searchBooksByLiteraryGenre(String literaryGenre) {
        List<Book> books = bookRepository.findByLiteraryGenre(literaryGenre);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET available Books */
    @Override
    public List<BookDTO> getAvailableBooks() {
        List<Book> books = bookRepository.findByAvailableTrue();
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET books in stock */
    @Override
    public List<BookDTO> searchBooksByStockGreaterThan(Integer stock) {
        List<Book> books = bookRepository.findByStockGreaterThan(stock);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET books with price btw min-max */
    @Override
    public List<BookDTO> searchBooksByPriceBetween(Double minPrice, Double maxPrice) {
        List<Book> books = bookRepository.findByPriceBetween(minPrice, maxPrice);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET Book by author */
    @Override
    public List<BookDTO> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** GET book by isb */
    @Override
    public BookDTO searchBookByIsbn(String isbn) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }
        return mapperBooks.bookToBookDTO(bookOptional.get());
    }

    /** GET book by description or parcial description */
    @Override
    public List<BookDTO> searchBooksByDescription(String description) {
        List<Book> books = bookRepository.searchByDescription(description);
        return books.stream().map(mapperBooks::bookToBookDTO).collect(Collectors.toList());
    }

    /** Upload Image by cloudinary */
    @Override
    public BookDTO updateImage(String id, String newImageUrl) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setImageUrl(newImageUrl);
        return mapperBooks.bookToBookDTO(book);
    }

}
