package com.porfolio.books_microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.porfolio.books_microservice.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByLiteraryGenre(String literaryGenre);

    List<Book> findByAvailableTrue();

    List<Book> findByStockGreaterThan(Integer stock);

    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    Optional<Book> findByIsbn(String isbn);

    @Query("{ 'description': { $regex: ?0, $options: 'i' } }")
    List<Book> searchByDescription(String description);

    Page<Book> findAll(Pageable pageable);

    @Query("{ '_id': ?0 }")
    void softDeleteById(String id);
}
