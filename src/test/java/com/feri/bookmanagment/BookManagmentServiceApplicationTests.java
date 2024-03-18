package com.feri.bookmanagment;

import com.feri.bookmanagment.controller.BookController;
import com.feri.bookmanagment.model.Book;
import com.feri.bookmanagment.repository.BookRepository;
import com.feri.bookmanagment.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookManagmentServiceApplicationTests {

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	// Preverja, ali metoda za pridobivanje vseh knjig vrača pravilen seznam knjig.

	public void testGetAllBooks() {
		// Priprava testnih podatkov
		Book book1 = new Book("1", "Title1", "Author1", "ISBN1", "Genre1");
		Book book2 = new Book("2", "Title2", "Author2", "ISBN2", "Genre2");
		List<Book> expectedBooks = Arrays.asList(book1, book2);

		// Določitev obnašanja mock-a
		when(bookService.getAllBooks()).thenReturn(expectedBooks);

		// Klic metode, ki jo testiramo
		List<Book> actualBooks = bookController.getAllBooks();

		// Preverjanje rezultatov
		assertEquals(expectedBooks, actualBooks);
	}
	@Test
	// Preverja, ali metoda za pridobivanje knjige po ID-ju vrača knjigo, če ta obstaja.

	public void testGetBookByIdWhenBookExists() {
		// Priprava testnih podatkov
		String id = "1";
		Book expectedBook = new Book(id, "Title", "Author", "ISBN", "Genre");

		// Določitev obnašanja mock-a
		when(bookService.getBookById(id)).thenReturn(Optional.of(expectedBook));

		// Klic metode, ki jo testiramo
		ResponseEntity<?> response = bookController.getBookById(id);

		// Preverjanje rezultatov
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedBook, response.getBody());
	}
	@Test
	// Preverja, ali metoda za pridobivanje knjige po ID-ju vrača status 404, če knjiga ne obstaja.

	public void testGetBookByIdWhenBookDoesNotExist() {
		// Priprava testnih podatkov
		String id = "1";

		// Določitev obnašanja mock-a
		when(bookService.getBookById(id)).thenReturn(Optional.empty());

		// Klic metode, ki jo testiramo
		ResponseEntity<?> response = bookController.getBookById(id);

		// Preverjanje rezultatov
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	// Preverja, ali metoda za ustvarjanje nove knjige pravilno shrani knjigo in vrne ustrezne podatke.

	public void testCreateBook() {
		// Priprava testnih podatkov
		Book book = new Book("1", "Title", "Author", "ISBN", "Genre");

		// Določitev obnašanja mock-a
		when(bookService.saveBook(book)).thenReturn(book);

		// Klic metode, ki jo testiramo
		ResponseEntity<Book> response = bookController.createBook(book);

		// Preverjanje rezultatov
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(book, response.getBody());
		verify(bookService).saveBook(book); // Preveri, ali je bila metoda saveBook() klicana z določenimi argumenti
	}
	@Test
	// Preverja, ali metoda za posodabljanje knjige pravilno posodobi podatke o obstoječi knjigi.

	public void testUpdateBook() {
		// Priprava testnih podatkov
		String id = "1";
		Book bookDetails = new Book("1", "Updated Title", "Updated Author", "Updated ISBN", "Updated Genre");
		Book existingBook = new Book("1", "Title", "Author", "ISBN", "Genre");

		// Določitev obnašanja mock-a
		when(bookService.getBookById(id)).thenReturn(Optional.of(existingBook));
		when(bookService.saveBook(existingBook)).thenReturn(existingBook);

		// Klic metode, ki jo testiramo
		ResponseEntity<?> response = bookController.updateBook(id, bookDetails);

		// Preverjanje rezultatov
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(existingBook, response.getBody());
		verify(bookService).getBookById(id); // Preveri, ali je bila metoda getBookById() klicana z določenimi argumenti
		verify(bookService).saveBook(existingBook); // Preveri, ali je bila metoda saveBook() klicana z določenimi argumenti
	}
	// Preverja, ali metoda za brisanje knjige pravilno izbriše knjigo, če ta obstaja, in vrne ustrezno sporočilo.

	@Test
	public void testDeleteBook_BookExists() {
		// Priprava testnih podatkov
		String bookId = "1";
		Book book = new Book(bookId, "Title", "Author", "ISBN", "Genre");

		// Priprava obnašanja mock-a
		when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

		// Klic metode, ki jo testiramo
		ResponseEntity<?> response = bookController.deleteBook(bookId);

		// Preverjanje rezultata
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Knjiga z ID " + bookId + " je bila uspešno izbrisana.", response.getBody());

		// Preverjanje, ali je bila metoda deleteBook iz klica servisa korektno klicana
		verify(bookService, times(1)).deleteBook(bookId);
	}
	// Preverja, ali metoda za brisanje knjige vrne napako, če knjiga, ki jo želimo izbrisati, ne obstaja.

	@Test
	public void testDeleteBook_BookNotExists() {
		// Priprava testnih podatkov
		String bookId = "1";

		// Priprava obnašanja mock-a
		when(bookService.getBookById(bookId)).thenReturn(Optional.empty());

		// Klic metode, ki jo testiramo
		ResponseEntity<?> response = bookController.deleteBook(bookId);

		// Preverjanje rezultata
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Knjiga z ID " + bookId + " ni bila najdena za brisanje.", response.getBody());

		// Preverjanje, ali metoda deleteBook ni klicala metode deleteBook servisa
		verify(bookService, never()).deleteBook(bookId);
	}
	// Preverja, ali metoda za iskanje knjig po naslovu pravilno vrne seznam knjig, ki vsebujejo dani niz v naslovu.

	@Test
	public void testGetBooksByTitle_TitleExists() {
		// Priprava testnih podatkov
		String title = "Title";
		Book book1 = new Book("1", title, "Author1", "ISBN1", "Genre1");
		Book book2 = new Book("2", title, "Author2", "ISBN2", "Genre2");
		List<Book> expectedBooks = Arrays.asList(book1, book2);

		// Priprava obnašanja mock-a
		when(bookService.getBooksByTitle(title)).thenReturn(expectedBooks);

		// Klic metode, ki jo testiramo
		List<Book> response = bookController.getBooksByTitle(title);

		// Preverjanje rezultatov
		assertEquals(expectedBooks.size(), response.size());
		assertEquals(expectedBooks, response);
	}
	// Preverja, ali metoda za iskanje knjig po avtorju pravilno vrne seznam knjig, ki jih je napisal določen avtor.

	@Test
	public void testGetBooksByAuthor_AuthorExists() {
		// Priprava testnih podatkov
		String author = "Author";
		Book book1 = new Book("1", "Title1", author, "ISBN1", "Genre1");
		Book book2 = new Book("2", "Title2", author, "ISBN2", "Genre2");
		List<Book> expectedBooks = Arrays.asList(book1, book2);

		// Priprava obnašanja mock-a
		when(bookService.getBooksByAuthor(author)).thenReturn(expectedBooks);

		// Klic metode, ki jo testiramo
		List<Book> response = bookController.getBooksByAuthor(author);

		// Preverjanje rezultatov
		assertEquals(expectedBooks.size(), response.size());
		assertEquals(expectedBooks, response);
	}
	// Preverja, ali metoda za iskanje knjig po žanru pravilno vrne seznam knjig, ki spadajo v določen žanr.

	@Test
	public void testGetBooksByGenre_GenreExists() {
		// Priprava testnih podatkov
		String genre = "Fiction";
		Book book1 = new Book("1", "Title1", "Author1", "ISBN1", genre);
		Book book2 = new Book("2", "Title2", "Author2", "ISBN2", genre);
		List<Book> expectedBooks = Arrays.asList(book1, book2);

		// Priprava obnašanja mock-a
		when(bookService.getBooksByGenre(genre)).thenReturn(expectedBooks);

		// Klic metode, ki jo testiramo
		List<Book> response = bookController.getBooksByGenre(genre);

		// Preverjanje rezultatov
		assertEquals(expectedBooks.size(), response.size());
		assertEquals(expectedBooks, response);
	}

}





