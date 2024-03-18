package com.feri.bookmanagment.repository;

import com.feri.bookmanagment.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // Metoda za iskanje po naslovu
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Metoda za iskanje po avtorju
    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByGenreContainingIgnoreCase(String genre);


}
