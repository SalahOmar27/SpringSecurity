package com.salah.springSecurity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salah.springSecurity.entity.Book;
import com.salah.springSecurity.exceptions.BookNotFoundException;
import com.salah.springSecurity.repository.BookRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService implements BookServiceInterface {
	private final BookRepository bookRepository;
	private final EntityManager entityManager;

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book add(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> findById(Long bookId) {
		return Optional.ofNullable(bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("No book found with the id : " + bookId)));
		// return Optional.empty();

	}

	@Override
	public void delete(Long bookId) {
		Optional<Book> theBook = findById(bookId);
		theBook.ifPresent(book -> bookRepository.deleteById(book.getId()));

	}

	@Override
	public Book update(Book book) {
		return bookRepository.save(book);
	}
	
	

}
