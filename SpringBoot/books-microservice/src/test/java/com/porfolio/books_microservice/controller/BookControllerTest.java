package com.porfolio.books_microservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.porfolio.books_microservice.dto.BookCreationDTO;
import com.porfolio.books_microservice.dto.BookDTO;
import com.porfolio.books_microservice.exception.BookNotFoundException;
import com.porfolio.books_microservice.service.IBookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private IBookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void testCreateBook() {
        BookCreationDTO creationDTO = new BookCreationDTO();
        creationDTO.setTitle("TestBook");
        creationDTO.setAuthor("TestBook");
        creationDTO.setLiteraryGenre("TestBook");
        creationDTO.setPrice(10.2);
        creationDTO.setStock(100);
        creationDTO.setAvailable(true);
        creationDTO.setImageUrl("www.url.com");
        creationDTO.setDescription("Description test book");

        BookDTO responseDTO = new BookDTO();
        responseDTO.setTitle("Test Book");
        responseDTO.setAuthor("Test Author");
        responseDTO.setLiteraryGenre("Fiction");
        responseDTO.setPrice(29.99);
        responseDTO.setAvailable(true);
        responseDTO.setImageUrl("www.url.com");
        responseDTO.setDescription("This is a test book.");

        when(bookService.createBook(any(BookCreationDTO.class))).thenReturn(responseDTO);

        ResponseEntity<BookDTO> response = bookController.createBook(creationDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Book", response.getBody().getTitle());
        verify(bookService, times(1)).createBook(any(BookCreationDTO.class));
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDTO> mockPage = new PageImpl<>(List.of(new BookDTO()));
        when(bookService.getAllBooks(pageable)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<BookDTO>> response = bookController.getAllBooks(pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        verify(bookService, times(1)).getAllBooks(pageable);
    }

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        String id = "123";
        BookDTO mockBook = new BookDTO();
        mockBook.setTitle("Existing Book");
        when(bookService.getBookById(id)).thenReturn(mockBook);

        // Act
        ResponseEntity<BookDTO> response = bookController.getBookById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Existing Book", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(id);
    }

    @Test
    void testGetBookByIdNotFound() {
        // Arrange
        String id = "invalid-id";
        when(bookService.getBookById(id)).thenThrow(new BookNotFoundException("Book not found"));

        // Act & Assert
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookController.getBookById(id);
        });
        assertEquals("Book not found", exception.getMessage());
        verify(bookService, times(1)).getBookById(id);
    }

    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        String id = "123";
        BookCreationDTO updateDTO = new BookCreationDTO();
        updateDTO.setTitle("Updated Book");

        BookDTO updatedBook = new BookDTO();
        updatedBook.setTitle("Updated Book");

        when(bookService.updateBook(eq(id), any(BookCreationDTO.class))).thenReturn(updatedBook);

        // Act
        ResponseEntity<BookDTO> response = bookController.updateBook(id, updateDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Book", response.getBody().getTitle());
        verify(bookService, times(1)).updateBook(eq(id), any(BookCreationDTO.class));
    }

    @Test
    void testPatchBook() throws Exception {
        // Arrange
        String id = "123";
        BookCreationDTO patchDTO = new BookCreationDTO();
        patchDTO.setTitle("Patched Book");

        BookDTO patchedBook = new BookDTO();
        patchedBook.setTitle("Patched Book");

        when(bookService.patchBook(eq(id), any(BookCreationDTO.class))).thenReturn(patchedBook);

        // Act
        ResponseEntity<BookDTO> response = bookController.patchBook(id, patchDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Patched Book", response.getBody().getTitle());
        verify(bookService, times(1)).patchBook(eq(id), any(BookCreationDTO.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        String id = "123";

        doNothing().when(bookService).deleteBook(id);

        // Act
        ResponseEntity<Void> response = bookController.deleteBook(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(id);
    }

    @Test
    void testSearchBooksByTitle() {
        // Arrange
        String title = "Java";
        List<BookDTO> mockBooks = List.of(new BookDTO(), new BookDTO());
        when(bookService.searchBooksByTitle(title)).thenReturn(mockBooks);

        // Act
        ResponseEntity<List<BookDTO>> response = bookController.searchBooksByTitle(title);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).searchBooksByTitle(title);
    }

    @Test
    void testSearchBooksByAuthor() {
        // Arrange
        String author = "John Doe";
        List<BookDTO> mockBooks = List.of(new BookDTO(), new BookDTO());
        when(bookService.searchBooksByAuthor(author)).thenReturn(mockBooks);

        // Act
        ResponseEntity<List<BookDTO>> response = bookController.searchBooksByAuthor(author);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(bookService, times(1)).searchBooksByAuthor(author);
    }

    @Test
    void testSearchBookByIsbn() {
        // Arrange
        String isbn = "1234567890";
        BookDTO mockBook = new BookDTO();
        mockBook.setTitle("Test Book");
        when(bookService.searchBookByIsbn(isbn)).thenReturn(mockBook);

        // Act
        ResponseEntity<BookDTO> response = bookController.serachBookByIsbn(isbn);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Book", response.getBody().getTitle());
        verify(bookService, times(1)).searchBookByIsbn(isbn);
    }

}
