package com.mac2work.orders.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class OrdersApiError {
	private HttpStatus httpStatus;
	private LocalDateTime timestamp;
	private String message;
	
	private OrdersApiError() {
		timestamp = LocalDateTime.now();
	}


	public OrdersApiError(HttpStatus httpStatus, String message) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
