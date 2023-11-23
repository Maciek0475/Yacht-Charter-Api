package com.mac2work.userpanel.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserApiError {
	private HttpStatus httpStatus;
	private LocalDateTime timestamp;
	private String message;
	
	private UserApiError() {
		timestamp = LocalDateTime.now();
	}


	public UserApiError(HttpStatus httpStatus, String message) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
	}
}

