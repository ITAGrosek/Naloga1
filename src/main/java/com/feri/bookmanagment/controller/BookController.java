package com.feri.bookmanagment.controller;

import com.feri.bookmanagment.model.Book;
import com.feri.bookmanagment.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        log.info("Pridobljene vse knjige. Število knjig: {}", books.size());
        return books;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(book -> {
                    log.info("Knjiga z ID {} najdena.", id);
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    log.warn("Knjiga z ID {} ni bila najdena.", id);
                    return ResponseEntity.notFound().build();
                });
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.saveBook(book);
        log.info("Nova knjiga dodana: ID={}, Naslov={}, Avtor={}", createdBook.getId(), createdBook.getTitle(), createdBook.getAuthor());
        return ResponseEntity.ok(createdBook);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody Book bookDetails) {
        return bookService.getBookById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setGenre(bookDetails.getGenre());
                    Book updatedBook = bookService.saveBook(book);
                    log.info("Knjiga posodobljena: ID={}, Naslov={}, Avtor={}, ISBN={}, Žanr={}",
                            updatedBook.getId(), updatedBook.getTitle(), updatedBook.getAuthor(),
                            updatedBook.getIsbn(), updatedBook.getGenre());
                    return ResponseEntity.ok(updatedBook);
                })
                .orElseGet(() -> {
                    log.warn("Knjiga z ID {} ni bila najdena za posodobitev.", id);
                    return ResponseEntity.notFound().build();
                });
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(book -> {
                    bookService.deleteBook(id);
                    log.info("Knjiga z ID {} je bila uspešno izbrisana.", id);
                    return ResponseEntity.ok("Knjiga z ID " + id + " je bila uspešno izbrisana.");
                })
                .orElseGet(() -> {
                    log.warn("Knjiga z ID {} ni bila najdena za brisanje.", id);
                    return ResponseEntity.badRequest().body("Knjiga z ID " + id + " ni bila najdena za brisanje.");
                });
    }

    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        log.info("Iskanje knjig z naslovom, ki vsebuje: {}", title);
        List<Book> books = bookService.getBooksByTitle(title);
        if(books.isEmpty()) {
            log.info("Ni najdenih knjig z naslovom, ki vsebuje: {}", title);
        } else {
            log.info("Najdenih knjig z naslovom, ki vsebuje {}: {}", title, books.size());
        }
        return books;
    }


    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        log.info("Iskanje knjig avtorja: {}", author);
        List<Book> books = bookService.getBooksByAuthor(author);
        if(books.isEmpty()) {
            log.info("Ni najdenih knjig za avtorja: {}", author);
        } else {
            log.info("Najdeno {} knjig za avtorja: {}", books.size(), author);
        }
        return books;
    }


    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        log.info("Iskanje knjig po žanru: {}", genre);
        List<Book> books = bookService.getBooksByGenre(genre);
        if (books.isEmpty()) {
            log.info("Ni najdenih knjig za žanr: {}", genre);
        } else {
            log.info("Najdeno {} knjig za žanr: {}", books.size(), genre);
        }
        return books;
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Mikrostoritev za upravljanje knjig deluje!");
    }
}
