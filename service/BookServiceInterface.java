package com.salah.springSecurity.service;

import java.util.List;
import java.util.Optional;

import com.salah.springSecurity.entity.Book;

public interface BookServiceInterface {
	List<Book> getAllBooks();

	Book add(Book book);

	Optional<Book> findById(Long bookId);

	void delete(Long bookId);

	Book update(Book book);
	

}
