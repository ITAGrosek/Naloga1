package com.feri.bookmanagment.service;

import com.feri.bookmanagment.model.Book;
import com.feri.bookmanagment.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create/Update knjige
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Read knjige po ID-ju
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    // Read vseh knjig
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Read knjig po naslovu
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Read knjig po avtorju
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // Delete knjige po ID-ju
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreContainingIgnoreCase(genre);
    }

}
