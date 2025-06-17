package com.porfolio.books_microservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.exception.BookNotFoundException;
import com.porfolio.books_microservice.model.Book;
import com.porfolio.books_microservice.repository.BookRepository;
import com.porfolio.books_microservice.utils.MapperBooks;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MapperBooks mapperBooks;

    @InjectMocks
    private BookServiceImpl bookService;

    private static final String BOOK_ID = "123";
    private static final String ISBN = "ISBN-12345";

    @Test
    void testCreateBook() {
        // Arrange
        BookCreationDTO creationDTO = new BookCreationDTO();
        creationDTO.setTitle("Test Book");
        creationDTO.setAuthor("Test Author");

        Book bookEntity = new Book();
        bookEntity.setId(BOOK_ID);
        bookEntity.setTitle("Test Book");
        bookEntity.setAuthor("Test Author");

        when(mapperBooks.bookCreationDTOtoBook(creationDTO)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        BookDTO expectedDTO = new BookDTO();
        expectedDTO.setTitle("Test Book");
        expectedDTO.setAuthor("Test Author");

        when(mapperBooks.bookToBookDTO(bookEntity)).thenReturn(expectedDTO);

        // Act
        BookDTO result = bookService.createBook(creationDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals("Test Author", result.getAuthor());
        verify(bookRepository, times(1)).save(bookEntity);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(new Book(), new Book());
        Page<Book> mockPage = new PageImpl<>(books);

        when(bookRepository.findAll(pageable)).thenReturn(mockPage);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");

        when(mapperBooks.bookToBookDTO(any(Book.class))).thenReturn(bookDTO);

        // Act
        Page<BookDTO> result = bookService.getAllBooks(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetBookById_Success() throws BookNotFoundException {
        // Arrange
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle("Test Book");

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");

        when(mapperBooks.bookToBookDTO(book)).thenReturn(bookDTO);

        // Act
        BookDTO result = bookService.getBookById(BOOK_ID);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(BOOK_ID);
    }

    @Test
    void testGetBookById_NotFound() {
        // Arrange
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById(BOOK_ID);
        });
        assertEquals("Book with ID " + BOOK_ID + " not found.", exception.getMessage());
        verify(bookRepository, times(1)).findById(BOOK_ID);
    }

    @Test
    void testUpdateBook_Success() throws BookNotFoundException {
        // Arrange
        BookCreationDTO updateDTO = new BookCreationDTO();
        updateDTO.setTitle("Updated Book");

        Book existingBook = new Book();
        existingBook.setId(BOOK_ID);
        existingBook.setTitle("Old Title");

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        BookDTO updatedDTO = new BookDTO();
        updatedDTO.setTitle("Updated Book");

        when(mapperBooks.bookToBookDTO(existingBook)).thenReturn(updatedDTO);

        // Act
        BookDTO result = bookService.updateBook(BOOK_ID, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Book", result.getTitle());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testPatchBook_Success() throws BookNotFoundException {
        // Arrange
        BookCreationDTO patchDTO = new BookCreationDTO();
        patchDTO.setTitle("Patched Book");

        Book existingBook = new Book();
        existingBook.setId(BOOK_ID);
        existingBook.setTitle("Old Title");

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        BookDTO patchedDTO = new BookDTO();
        patchedDTO.setTitle("Patched Book");

        when(mapperBooks.bookToBookDTO(existingBook)).thenReturn(patchedDTO);

        // Act
        BookDTO result = bookService.patchBook(BOOK_ID, patchDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Patched Book", result.getTitle());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testDeleteBook_Success() throws BookNotFoundException {
        // Arrange
        Book book = new Book();
        book.setId(BOOK_ID);

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).softDeleteById(BOOK_ID);

        // Act
        bookService.deleteBook(BOOK_ID);

        // Assert
        verify(bookRepository, times(1)).softDeleteById(BOOK_ID);
    }

    @Test
    void testSearchBooksByTitle() {
        // Arrange
        String title = "Java";
        List<Book> books = List.of(new Book(), new Book());

        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(books);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Java Book");

        when(mapperBooks.bookToBookDTO(any(Book.class))).thenReturn(bookDTO);

        // Act
        List<BookDTO> result = bookService.searchBooksByTitle(title);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(title);
    }

    @Test
    void testSearchBookByIsbn_Success() throws BookNotFoundException {
        // Arrange
        Book book = new Book();
        book.setIsbn(ISBN);
        book.setTitle("Test Book");

        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.of(book));

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");

        when(mapperBooks.bookToBookDTO(book)).thenReturn(bookDTO);

        // Act
        BookDTO result = bookService.searchBookByIsbn(ISBN);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findByIsbn(ISBN);
    }

    @Test
    void testSearchBookByIsbn_NotFound() {
        // Arrange
        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.searchBookByIsbn(ISBN);
        });
        assertEquals("Book with ISBN " + ISBN + " not found.", exception.getMessage());
        verify(bookRepository, times(1)).findByIsbn(ISBN);
    }

}
