package com.mac2work.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SearchApiError {
	private HttpStatus httpStatus;
	private LocalDateTime timestamp;
	private String message;
	
	private SearchApiError() {
		timestamp = LocalDateTime.now();
	}


	public SearchApiError(HttpStatus httpStatus, String message) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
	}
}

