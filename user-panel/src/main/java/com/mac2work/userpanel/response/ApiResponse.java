package com.mac2work.userpanel.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
	
	private Boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

}
