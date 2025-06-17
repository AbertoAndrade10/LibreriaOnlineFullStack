package com.porfolio.books_microservice.controller;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.service.IBookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    /** CREATE a new book */
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookCreationDTO bookCreationDTO) {
        BookDTO createdBook = bookService.createBook(bookCreationDTO);
        return ResponseEntity.status(201).body(createdBook); // return 201 created
    }

    /** GET all books with pagination */
    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(Pageable pageable) {
        Page<BookDTO> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }

    /** GET a book by ID */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /** UPDATE a book by ID (full update) */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String id, @RequestBody BookCreationDTO bookCreationDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookCreationDTO);
        return ResponseEntity.ok(updatedBook);
    }

    /** PATCH a book by ID (partial update) */
    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> patchBook(@PathVariable String id, @RequestBody BookCreationDTO bookCreationDTO) {
        BookDTO patchedBook = bookService.patchBook(id, bookCreationDTO);
        return ResponseEntity.ok(patchedBook);
    }

    /** DELETE a book by ID (solft delete) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // 204 no content
    }

    /** SEARCH a books by title */
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    /** SEARCH a boks by literary genre */
    @GetMapping("/search/genre")
    public ResponseEntity<List<BookDTO>> searchBooksByLiteraryGenre(@RequestParam String literaryGenre) {
        List<BookDTO> books = bookService.searchBooksByLiteraryGenre(literaryGenre);
        return ResponseEntity.ok(books);
    }

    /** GET available books */
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        List<BookDTO> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    /** SEARCH books with stock greater than a value */
    @GetMapping("/search/stock")
    public ResponseEntity<List<BookDTO>> searchBooksByStockGreaterThan(@RequestParam Integer stock) {
        List<BookDTO> books = bookService.searchBooksByStockGreaterThan(stock);
        return ResponseEntity.ok(books);

    }

    /** SERACH books within a prince range */
    @GetMapping("/search/price")
    public ResponseEntity<List<BookDTO>> searchBooksByPriceBtw(@RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<BookDTO> books = bookService.searchBooksByPriceBetween(minPrice, maxPrice);
        return ResponseEntity.ok(books);
    }

    /** SEARCH book by author */
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> searchBooksByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    /** SEARCH book by ISBN */
    @GetMapping("/search/isbn")
    public ResponseEntity<BookDTO> serachBookByIsbn(@RequestParam String isbn) {
        BookDTO book = bookService.searchBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    /** SEARCH books by description */
    @GetMapping("/search/description")
    public ResponseEntity<List<BookDTO>> searchBooksByDescription(@RequestParam String description) {
        List<BookDTO> books = bookService.searchBooksByDescription(description);
        return ResponseEntity.ok(books);
    }

}
