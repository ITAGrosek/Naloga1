
/*
package com.feri.bookmanagment.repository;

import com.feri.bookmanagment.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Priprava
        String title = "TestTitle";
        bookRepository.save(new Book(null, title, "TestAuthor", "TestISBN", "TestGenre"));

        // Izvedba
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title.toLowerCase());

        // Preverjanje
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo(title);
    }

    // Podobno lahko dodate več testov za preverjanje ostalih metod repozitorija
}
*/