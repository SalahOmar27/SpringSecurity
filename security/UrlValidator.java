package com.salah.springSecurity.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UrlValidator {

	private static final String URL_PATTERN = "^https?:\\/\\/example\\.com\\/([a-z0-9_-]+\\/)*[a-z0-9_-]+$";

	public boolean isValidUrl(String url) {
		return url.matches(URL_PATTERN);
	}

	public void validateUrl(String url) {
		if (!isValidUrl(url)) {
			throw new IllegalArgumentException("Invalid URL");
		}
	}

}
