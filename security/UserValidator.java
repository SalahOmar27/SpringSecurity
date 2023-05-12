package com.salah.springSecurity.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.salah.springSecurity.entity.User;

@Component
@Validated
public class UserValidator {
	public void validateUser(User user) {
		if (!isValidEmail(user.getEmail())) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!isValidPassword(user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}
//		if (!isValidDate(user.getBirthDate())) {
//			throw new IllegalArgumentException("Invalid birth date");
//		}
	}

	private boolean isValidEmail(String email) {
		// Use regular expression to validate email format
		return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}

	private boolean isValidPassword(String password) {
		// Password should have at least 8 characters, at least one upper case letter,
		// one lower case letter, and one number
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
	}

//	private boolean isValidDate(String date) {
//		// Date should be in the format "yyyy-MM-dd"
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		dateFormat.setLenient(false);
//		try {
//			dateFormat.parse(date.trim());
//		} catch (ParseException pe) {
//			return false;
//		}
//		return true;
//	}

}
