package com.salah.springSecurity.exceptions;

public class BookNotFoundException extends RuntimeException {
	public BookNotFoundException(String message) {
		super(message);
	}

}
