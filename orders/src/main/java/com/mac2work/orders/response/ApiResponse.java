package com.mac2work.orders.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
	
	private Boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

}
