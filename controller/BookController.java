package com.salah.springSecurity.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salah.springSecurity.entity.Book;
import com.salah.springSecurity.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "https://localhost/")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@Secured("ROLE_ADMIN")
	@GetMapping("/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	@GetMapping("/book/{id}")
	public Optional<Book> getById(@PathVariable("id") Long bookId) {
		return bookService.findById(bookId);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/add")
	public ResponseEntity<Book> add(@RequestBody Book book) {
		return new ResponseEntity<>(bookService.add(book), CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/update")
	public ResponseEntity<Book> update(@RequestBody Book theBook) {
		return new ResponseEntity<>(bookService.update(theBook), OK);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/book/delete/{id}")
	public void delete(@PathVariable("id") Long bookId) {
		bookService.delete(bookId);
	}

}
